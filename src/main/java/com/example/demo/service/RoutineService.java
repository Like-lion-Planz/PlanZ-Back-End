package com.example.demo.service;

import com.example.demo.domain.Calendar;
import com.example.demo.domain.Routine;
import com.example.demo.domain.ToDo;
import com.example.demo.domain.User;
import com.example.demo.dto.RoutineInfoDto;
import com.example.demo.dto.RoutineSaveDto;
import com.example.demo.repository.CalendarRepository;
import com.example.demo.repository.RoutineRepository;
import com.example.demo.repository.ToDoRepository;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.nimbusds.oauth2.sdk.Request;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final UserService userService;
    private final CalendarRepository calendarRepository;
    private final ToDoRepository toDoRepository;
    private final CalendarService calendarService;

    @Value("${openai.secret-key}")
    private String apiKey;

    private final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();
    private final Gson gson = new Gson();

    public void createRoutine(RoutineSaveDto routineSaveDto) throws IOException {

        User user = userService.findByUser();
        Routine routine = routineSaveDto.toEntity();
        routine.setUser(user);
        routineRepository.save(routine);
        ToDo toDo = new ToDo();
        toDo.setRoutine(routine);
        toDo.setNotification(routine.getStartTime());
        toDo.setContent("일과 시작");
        toDo.setNotificationIsTrue(false);
        toDoRepository.save(toDo);

        ToDo toDo2 = new ToDo();
        toDo2.setRoutine(routine);
        toDo2.setNotification(routine.getEndTime());
        toDo2.setContent("일과 끝");
        toDo2.setNotificationIsTrue(false);
        toDoRepository.save(toDo2);
        String content = createGptTodo(routine);

        calendarService.saveRoutineDates(routine.getId(),routineSaveDto.dates());
        Type listType = new TypeToken<List<TimeContent>>() {}.getType();
        List<TimeContent> timeContents = gson.fromJson(content, listType);
        System.out.println("gpt반환: " + timeContents);
        LocalTime wakeUpTime = null;
        LocalTime sleepTime = null;
        for (TimeContent timeContent : timeContents) {
            if ("기상시간".equals(timeContent.getContent())) {
                wakeUpTime = LocalTime.parse(timeContent.getTime());
            } else if ("취침시간".equals(timeContent.getContent())) {
                sleepTime = LocalTime.parse(timeContent.getTime());
            }
        }
        ToDo wakeUpToDo = new ToDo();
        wakeUpToDo.setRoutine(routine);
        wakeUpToDo.setContent("기상시간");
        wakeUpToDo.setNotification(wakeUpTime);
        wakeUpToDo.setCalendar(null);
        wakeUpToDo.setNotificationIsTrue(false);
        toDoRepository.save(wakeUpToDo);

        ToDo sleepToDO = new ToDo();
        sleepToDO.setRoutine(routine);
        sleepToDO.setContent("취침시간");
        sleepToDO.setNotification(sleepTime);
        sleepToDO.setCalendar(null);
        sleepToDO.setNotificationIsTrue(false);
        toDoRepository.save(sleepToDO);

        System.out.println("기상시간: " + wakeUpTime);
        System.out.println("취침시간: " + sleepTime);
    }
    public RoutineInfoDto getMyRoutine(Long id){
        User user = userService.findByUser();
        Routine routine = routineRepository.findRoutineByUserAndId(user,id);
        return new RoutineInfoDto(routine);
    }
    public List<RoutineInfoDto> myAllRoutine(){
        User user = userService.findByUser();
        List<Routine> routines = routineRepository.findRoutineByUser(user);
        return routines.stream()
                .map(RoutineInfoDto::new)
                .collect(Collectors.toList());
    }
    public List<RoutineInfoDto> getTodaysSchedule() {
        User user = userService.findByUser();
        LocalDate today = LocalDate.now();
        Long year = (long)today.getYear();
        Long month = (long)today.getMonthValue();
        Long day = (long)today.getDayOfMonth();
        List<Calendar> calendars = calendarRepository.findAllByYearAndMonthAndDayAndUser(year, month, day, user);
        Long a =1L;
        return calendars.stream()
                .map(calendar -> {
                    Routine routine = calendar.getRoutine();
                    return new RoutineInfoDto(routine,a);
                })
                .collect(Collectors.toList());
    }


    public List<RoutineInfoDto> getScheduleByDate(LocalDate date) {
        User user = userService.findByUser();
        Long year = (long)date.getYear();
        Long month = (long)date.getMonthValue();
        Long day = (long)date.getDayOfMonth();
        List<Calendar> calendars = calendarRepository.findAllByYearAndMonthAndDayAndUser(year, month, day, user);
        Long a =1L;
        return calendars.stream()
                .map(calendar -> {
                    Routine routine = calendar.getRoutine();
                    return new RoutineInfoDto(routine,a);
                })
                .collect(Collectors.toList());
    }

    private String createGptTodo(Routine routine) throws IOException {
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

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();
            System.out.println("Response: " + responseBody);
            String content = extractContentFromResponse(responseBody);
            return content;
        }
    }
    private String extractContentFromResponse(String responseBody) {
        JsonObject responseJson = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonArray choices = responseJson.getAsJsonArray("choices");
        JsonObject message = choices.get(0).getAsJsonObject().getAsJsonObject("message");
        String content = message.get("content").getAsString();

        content = content.replace("```json", "").replace("```", "").trim();

        JsonElement jsonElement;
        try {
            jsonElement = JsonParser.parseString(content);
        } catch (JsonSyntaxException e) {
            throw new IllegalStateException("Malformed JSON: " + content, e);
        }

        if (jsonElement.isJsonArray()) {
            JsonArray dataArray = jsonElement.getAsJsonArray();
            return dataArray.toString();
        } else {
            JsonArray dataArray = new JsonArray();
            dataArray.add(jsonElement);
            return dataArray.toString();
        }
    }

    private Map<String, Object> buildTodoPayload(Routine routine) {
        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", """
        해당 루틴 일 시작 시간과 종료 시간을 보고 추천 기상시간과 취침시간 데이터만 생성해서 이 두개의 데이터만 json형태로만 반환해줘. 형태는 [{"time": "07:00:00",
        "content": "기상시간"},{"time": "22:00:00","content": "취침시간"}] 와 같이 반환해주는데 json형태로 json데이터만을 반환하고 다른건 반환하지 마. JSON 데이터만 반환해줘.""");

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gpt-4o");
        payload.put("messages", new Object[]{message});
        payload.put("max_tokens", 300);

        return payload;
    }

    private class TimeContent {
        private String time;
        private String content;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}