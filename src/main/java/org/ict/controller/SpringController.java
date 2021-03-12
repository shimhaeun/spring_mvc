package org.ict.controller;

import java.util.ArrayList;

import org.ict.domain.BaseVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller //이거 안쓰면 컨트롤러 동작 안함
// 기본 url(localhost:8181/ 뒤에 spring/ 모든 패턴이 추가됨.
@RequestMapping("/spring/*")// * 와일드카드 ! 스프링 뒤에도 다음 패턴이 있다고 안내.
public class SpringController {
	
	private Model model;
	@RequestMapping("")//뒤에 아무것도 안오면 베이스로 작동시키겠다.
	public void base() {
		System.out.println("기본 url로 접속했습니다.");
	}
	
	
	//@RequestMapping은 여러 파라미터를 가짐
	//value 파라미터는 필수이고, url패턴을 기술함
	//method 파라미터는 생략이 가능하고 처리할 접속방식을 정의함
	//리턴 자료가 void인 경우, 주소.jsp파일을 화면에 뿌림
	//경로는 기본주소 이후에 추가로 붙은 주소를 경로로 한다. - return 자료가 void인 케이스
	//접속url : localhost:8181/spring/base
	@RequestMapping(value = "/base",
			method= {RequestMethod.GET, RequestMethod.POST})
	public void baseGet() {
		
		System.out.println("base get post");
	}
	
	//GetMapping은 오로지 get방식 접속만 정의할 때 사용
	//접속url : localhost:8181/spring/baseget
	@GetMapping("/baseGet")
	public void baseGet2() {
		System.out.println("base get!");
	}
	
	//마찬가지로 PostMapping은 오로지 Post방식 접속만 정의함
	@PostMapping("/basePost")
	public void basePost() {
		System.out.println("그냥 접속 할 수 없는 POST");
	}
	
	//get방식으로 접속하는 메서드 하나를 만들어주세요 자율적으로
	
	@GetMapping("/ict")
	public void ict() {
		
		System.out.println("ICT 인재개발원입니다.");
	}
	
	//BaseVO의 멤버변수들을 파라미터로 처리할 수 있는 메서드
	//메서드의 파라미터에 Class를 선언하면 getter와 setter가 있을 때 컨트롤러 내부적으로 파라미터를 객체에게 전달할 수 있다.
	//단, 이 경우는 클래스 내의 멤버변수와 파라미터 이름이 일치해야한다. /일치하는 게 없으면 안 받을 수도. 파라미터는 실행 오류 여부에 영향 ㄴㄴ
	//리턴타입 string인 경우 url 주소 무시하고 곧바로 리턴된 문자열.jsp를 views 폴더 하위에 배치해야 함
	@GetMapping("/vo")
	public String vo01(BaseVO vo) {
		
		System.out.println("" + vo);//클래스 생성만 하고 초기화 안 한 상황
		return "vo01"; //파일 이름을 리턴으로 따라감/ void는 접속주소 파일명 같아야함
		//string은 따로 놀 수 있음
	}
	
	//vo02.jsp를 spring폴더 하위에 저장하는 메서드 vo02 작성. 내부 코드 vo01과 일치.리턴문만.
	//스프링에서는 Model이라는 객체를 이용해 컨트롤러의 데이터를 뷰(.jsp)로 보내줌
	//1. 메서드의 파라미터 선언부에 추가로 Model 객체를 선언함
	//2. model.addAttribute("보낼이름", 보낼자료); 구문 작성
	//3. .jsp에서는 ${보낸이름}으로 처리 가능
	@GetMapping("/vo2")
	public String vo02(Model model, BaseVO vo) {
		
		System.out.println("" + vo);
		model.addAttribute("BaseVO", vo);
		return "spring/vo02";
	}
	//참조형 변수는 model.addAttribute를 사용하지 않아도 자동으로 전달을 해 줌
	//이 때 자료형의 맨 앞 글자만 소문잘 바꿔서 자동 전달
	//반면 기본형 변수는 자동 전달이 이루어지지 않는데 이 때 model.addAttribute를 쓸 수도 있지만
	//대신 @ModelAttribute를 써서 전달 가능
	//@ModelAttribute("보낼 이름")으로 선언
	//파라미터로 기본형 자료 선언시 반드시 자료가 전달되어야 함
	@GetMapping("/vo3")
	public String vo3(BaseVO vo, @ModelAttribute("num") int num, Model model) {
		System.out.println("회원번호  : " +num);
		//model.addAttribute()를 사용해 num도 전달해주세요.
		//model.addAttribute("num", num);
		
		return "spring/vo03";
	}
	//특정 주소 접속 시 redirect를 수행시키고 싶다면 //강제로 다른 페이지로 이
	//return하는 문자열 앞에 redirect:추가
	//url에서 가장 왼쪽에 적는 /는 기본 주소(localhost:8181/) //앞에 localhost 어쩌구 생략
	@GetMapping("/qwer")
	public String redirectTest() {
		System.out.println("/base로 redirect");
		return "redirect:/spring/";
	}
	
	//파일 업로드 페이지로 연결해주는 메서드
	@GetMapping("/exUpload")
	public void exUpload() {
		System.out.println("/exUpload....");
	}
	
	//파일은 POST방식으로 전송했기 때문에 @PostMapping 처리
	//ArrayList.forEach(n번 자료->{자료가 포함된 실행문..})
	//forEach도 결국 일반 반복문처럼 반복해서 자료갯수만큼
	//반복실행을 하기 위한 문법
	
	@PostMapping("/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> {//arraylist뒤에붙음 내부에 있는 요소를 갯수만큼 돌림
			System.out.println("------------------");
			System.out.println("name: "+ file.getOriginalFilename());
			System.out.println("size:" + file.getSize());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		});
	}
	
}
