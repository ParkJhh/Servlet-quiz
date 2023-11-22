package com.example.servletpro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

@WebServlet(name = "FamilyName", value = "/quizzes/quiz01/FamilyName")
public class quiz01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        //입력 받은 문자열 변환
        String s = req.getParameter("names");
        String ns = s.replaceAll(" ","");
        String[] array = ns.split(",");
        //성만 받을 배열
        String[] newArray = new String[array.length];

        out.println("입력 데이터 : " + ns);
        out.println("<hr>");
        //배열에 담기
        for (int i = 0; i < array.length; i++) {
            if (array[i].length() == 3 || array[i].length() == 2) {
                //성포함 3글자 혹은 2글자
                newArray[i] = array[i].substring(0, 1);
            } else if (array[i].length() == 4) {
                //성이 2글자 ex:남궁 //성이 3글자인 경우는 포기
                newArray[i] = array[i].substring(0, 2);
            }
        }
        //최빈값 저장
        HashMap<String, Integer> count = new HashMap<>();
        for (int i = 0; i < newArray.length; i++) {
            if (!count.containsKey(newArray[i])) {
                //키가 없는 경우 해시맵에 해당 성과 함께 초기값 1세팅
                count.put(newArray[i], 1);
            } else {
                //키가 존재하는 경우 value값 +
                count.put(newArray[i], count.get(newArray[i]) + 1);
            }
        }
        //hashmap 밸류값으로 오름차순 정렬 > 같은 수의 성씨가 두개 존재 할 경우 해결 되는가?
        //같은게 존재할 경우, key:김 value:4  key:박 value:4 로 박이 마지막에 위치하게 됨 > 김씨를 출력해야하나 박씨가 출력
        //성씨 해결 못해서 아래는 인터넷 참조 https://hianna.tistory.com/577
        //2. 3. value 기준 최대값을 가지는 key, value 찾기
        Comparator<Map.Entry<String, Integer>> comparator = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
                //equals > 단순 문자열이 같은지 비교  //compareTo > 문자열을 사전순으로 비교
                return e1.getValue().compareTo(e2.getValue());
            }
        };
        Entry<String, Integer> maxEntry = Collections.max(count.entrySet(), comparator);
        out.println(maxEntry.getKey() + "씨가 가장 많습니다. ");
        out.println("<p></p>");
        out.println("<a href=http://localhost:8080/servlet_pro_war_exploded/quizzes/quiz01/major-family-name.html >[검색 페이지로]</a>");
        out.println("<p></p>");
        out.println("<a href=http://localhost:8080/servlet_pro_war_exploded >[메인 페이지로]</a>");
    }
}

