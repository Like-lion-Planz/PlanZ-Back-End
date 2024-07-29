package com.example.demo.service;

import com.example.demo.domain.Routine;
import com.example.demo.domain.ToDo;
import com.example.demo.domain.User;
import com.example.demo.dto.RoutineSaveDto;
import com.example.demo.dto.ToDoInfoDto;
import com.example.demo.dto.ToDoSaveDto;
import com.example.demo.repository.RoutineRepository;
import com.example.demo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ToDoService {
    private final UserService userService;
    private final RoutineRepository routineRepository;
    private final ToDoRepository toDoRepository;
    public ToDoInfoDto createToDo(Long id, ToDoSaveDto toDoSaveDto){
        User user = userService.findByUser();
        ToDo toDo = toDoSaveDto.toEntity();
        Routine routine = (Routine) routineRepository.findRoutineByUserAndId(user,id);
        for(ToDo existingToDo : routine.getToDoList()){
            if(existingToDo.getNotification().equals(toDo.getNotification())){
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "이미 해당 시간에 할 일이 존재합니다.");
            }
        }
        toDo.setRoutine(routine);
        toDoRepository.save(toDo);
        return new ToDoInfoDto(toDo);
    }

    public boolean delete(Long routineId,Long toDoId){
        User user = userService.findByUser();
        Optional<ToDo> optionalToDo = toDoRepository.findById(toDoId);
        if(optionalToDo.isPresent()){
            ToDo toDo = optionalToDo.get();
            Routine routine = routineRepository.findById(routineId)
                    .orElseThrow(() -> new RuntimeException("루틴을 찾을 수 없습니다."));

            if(!routine.getUser().equals(user)){
                throw new RuntimeException("사용자가 일치하지 않습니다.");
            }
            toDoRepository.delete(toDo);
            return true;
        }else {
            throw new IllegalStateException("투두 못찾았습니다.");
        }
    }
    public boolean edit(Long routineId, Long toDoId, ToDoSaveDto toDoSaveDto) {
        User user = userService.findByUser();
        Optional<ToDo> optionalToDo = toDoRepository.findById(toDoId);

        if(optionalToDo.isPresent()){
            ToDo toDo = optionalToDo.get();
            Routine routine = routineRepository.findById(routineId)
                    .orElseThrow(() -> new RuntimeException("루틴을 찾을 수 없습니다."));

            if(!routine.getUser().equals(user)){
                throw new RuntimeException("사용자가 일치하지 않습니다.");
            }

            toDo.setContent(toDoSaveDto.content());
            toDo.setNotification(toDoSaveDto.notification());
            toDo.setNotificationIsTrue(true);
            toDoRepository.save(toDo);
            return true;
        }
        return false;
    }
    public boolean OnOff(Long routineId, Long toDoId) {
        User user = userService.findByUser();
        Optional<ToDo> optionalToDo = toDoRepository.findById(toDoId);

        if (optionalToDo.isPresent()) {
            ToDo toDo = optionalToDo.get();
            Routine routine = routineRepository.findById(routineId)
                    .orElseThrow(() -> new RuntimeException("루틴을 찾을 수 없습니다."));

            if (!routine.getUser().equals(user)) {
                throw new RuntimeException("사용자가 일치하지 않습니다.");
            }
            toDo.setNotificationIsTrue(!toDo.isNotificationIsTrue());

            toDoRepository.save(toDo);
            return true;
        } else {
            throw new RuntimeException("ToDo를 찾을 수 없습니다.");
        }
    }
}
