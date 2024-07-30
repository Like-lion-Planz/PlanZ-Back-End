package com.example.demo.service;

import com.example.demo.domain.Routine;
import com.example.demo.domain.ToDo;
import com.example.demo.domain.User;
import com.example.demo.dto.RoutineSaveDto;
import com.example.demo.dto.ToDoInfoDto;
import com.example.demo.dto.ToDoSaveDto;
import com.example.demo.repository.RoutineRepository;
import com.example.demo.repository.ToDoRepository;
import com.google.gson.Gson;
import com.nimbusds.oauth2.sdk.Request;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ToDoService {
    private final UserService userService;
    private final RoutineRepository routineRepository;
    private final ToDoRepository toDoRepository;

    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();

    @Value("${openai.secret-key}")
    private String apiKey;

    public ToDoInfoDto createToDo(Long id, ToDoSaveDto toDoSaveDto){
        User user = userService.findByUser();
        ToDo toDo = toDoSaveDto.toEntity();
        Routine routine = (Routine) routineRepository.findRoutineByUserAndId(user,id);
        toDo.setRoutine(routine);
        toDoRepository.save(toDo);
        return new ToDoInfoDto(toDo);
    }

    /*
    public String createGptTodo(String routine) throws IOException {
        String jsonPayload = gson.toJson(buildTodoPayload(routine));
        System.out.println("JSON payload: " + jsonPayload);

        MediaType JSON = MediaType.get("application/json; charset=utf-8");

        okhttp3.RequestBody body = okhttp3.RequestBody.create(jsonPayload, JSON);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .post(body)
                .build();

        // Response 처리
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    private Map<String, Object> buildTodoPayload(String routine) {
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", "해당 루틴을 보고 추천 기상시간과 취침시간 데이터 추가해서 json형태로만 반환해줘 " + routine);

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gpt-4");
        payload.put("messages", new Object[]{message});
        payload.put("max_tokens", 300);

        return payload;
    }
*/
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
            toDo.setNotification(toDoSaveDto.time());
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

    public void createAutoTodo(Long routineId){
        User user = userService.findByUser();
        Routine routine = routineRepository.findRoutineByUserAndId(user, routineId);

    }
}
