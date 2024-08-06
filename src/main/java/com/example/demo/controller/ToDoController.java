package com.example.demo.controller;

import com.example.demo.dto.RoutineSaveDto;
import com.example.demo.dto.ToDoInfoDto;
import com.example.demo.dto.ToDoSaveDto;
import com.example.demo.global.ResponseTemplate;
import com.example.demo.service.ToDoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;
    @PostMapping("/api/routine/{routineId}/create")
    public ResponseEntity<?> toDoCreate(@PathVariable("routineId")Long id, @RequestBody ToDoSaveDto toDoSaveDto){
        ToDoInfoDto toDoInfoDto = toDoService.createToDo(id,toDoSaveDto);
        return ResponseEntity.ok(toDoInfoDto);
        //return new ResponseTemplate<>(HttpStatus.OK, "투두 생성 성공",toDoService.createToDo(id,toDoSaveDto));
    }
    @DeleteMapping("/api/routine/{routineId}/{todoId}")
    public  ResponseEntity<?> toDoDelete(@PathVariable("routineId")Long routineId, @PathVariable("todoId")Long todoId){
        if(toDoService.delete(routineId,todoId)){
            return ResponseEntity.ok("투두 삭제 성공");
            //return new ResponseTemplate<>(HttpStatus.OK,"투두 삭제 성공");
        }
        return ResponseEntity.badRequest().body("투두 삭제 실패");
        //return new ResponseTemplate<>(HttpStatus.OK,"투두 삭제 실패");
    }
    @PatchMapping("/api/routine/{routineId}/{todoId}/edit")
    public ResponseEntity<?> toDoEdit(@PathVariable("routineId")Long routineId,@PathVariable("todoId")Long toDoId,@RequestBody ToDoSaveDto toDoSaveDto){
        if(toDoService.edit(routineId,toDoId,toDoSaveDto)){
            return ResponseEntity.ok("투두 수정 성공");
            //return new ResponseTemplate<>(HttpStatus.OK,"투두 수정 성공");
        }
        return ResponseEntity.badRequest().body("투두 수정 실패");
        //return new ResponseTemplate<>(HttpStatus.OK,"투두 수정 실패");
    }
    @PatchMapping("/api/routine/{routineId}/{todoId}/notification")
    public  ResponseEntity<?> toDoOnOff(@PathVariable("routineId")Long routineId,@PathVariable("todoId")Long toDoId){
        if(toDoService.OnOff(routineId,toDoId)){
            return ResponseEntity.ok("알람 OnOff 성공");
            //return new ResponseTemplate<>(HttpStatus.OK,"알람 OnOff 성공");
        }
        return ResponseEntity.badRequest().body("알람 OnOff 실패");
        //return new ResponseTemplate<>(HttpStatus.OK,"알람 OnOff 실패");
    }
}
