package com.wurigo.socialService.contoller;

import java.util.HashMap;


import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wurigo.socialService.domain.BoardVO;
import com.wurigo.socialService.domain.UserVO;
import com.wurigo.socialService.service.BoardService;
import com.wurigo.socialService.service.CustomerService;

@RestController
@CrossOrigin(origins="*", maxAge=3600)
@RequestMapping("/api/board")
public class BoardController {
	
	@Autowired
	BoardService boardService ; 
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/boardList")
	public List<BoardVO> boardList() throws Exception{
		//게시판 목록 
		return boardService.boardList();
	}
	@PostMapping("/insertBoard")
	public Map<String,Object> insertBoard(@RequestBody Map<String,Object> params )throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		int result = boardService.insertBoard(params);
		if(result==1) {
			map.put("message", "입력하신 글이 등록되었습니다.");
			map.put("success", "success");
		}else {
			map.put("message", "글 등록 중 에러가 발생했습니다.\n다시시도해주세요");			
		}
		return map;
	}
	@GetMapping("/boardSelect/{seq}")
	public BoardVO boardRecordSelect (@PathVariable int seq)throws Exception {
		BoardVO vo = boardService.boardRecordSelect(seq);
		return vo;
	}
	@PutMapping("/boardEdit/{seq}")
	public Map<String,Object>  boardRecordUpdate(@PathVariable int seq,@RequestBody Map<String,Object> params)throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		int result = boardService.boardRecordUpdate(params);
		System.out.println(result);
		if(result==1) {
			map.put("message", "게시물이 수정되었습니다.");
			map.put("success", "success");
		}else {
			map.put("message", "글 수정 중 에러가 발생했습니다.\n다시시도해주세요");		
		}
		return map;
	}
	@DeleteMapping("/boardDelete/{seq}")
	public Map<String,Object> boardRecordDelete(@PathVariable int seq)throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		System.out.println("테스트");
		int result = boardService.boardRecordDelete(seq);
		if(result==1) {
			map.put("message", "게시물이 삭제되었습니다.");
			map.put("success", "success");
		}else {
			map.put("message", "글 삭제 중 에러가 발생했습니다.\n다시시도해주세요");		
		}
		return map;
	}
	
	@GetMapping("/question")
	public List<BoardVO> boardQuestionList()throws Exception {
		return boardService.boardQuestionList(); 
	}
	
	@GetMapping("/questionContent/{seq}")
	public Map<String,Object> boardQuestionRecord(@PathVariable int seq)throws Exception {
		Map<String,Object> map= boardService.boardQuestionRecord(seq);
		return map;
		
	}
	
	@PostMapping("/question/contentInsert")
	public Map<String,Object> boardQuestionRecordInsert(@RequestBody Map<String,Object> params)throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		String customerNo = (String)params.get("customerNo");
		UserVO vo = customerService.userInfo(customerNo);
		params.put("customerNo",customerNo);
		params.put("username",vo.getUsername());
		params.put("email",vo.getEmail());
		params.put("title",params.get("title"));
		params.put("contents",params.get("contents"));
		int result = boardService.boardQuestionRecordInsert(params);
		if(result==1) {
			map.put("message", "게시물이 등록되었습니다.");
			map.put("success", "success");
		}else {
			map.put("message", "게시물 등록 중 에러가 발생했습니다.\n다시시도해주세요");		
		}
		return map;
	}
	
	@PutMapping("/question/contentUpdate")
	public Map<String,Object> boardQuestionRecordEdit(@RequestBody Map<String,Object> params)throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		int result = boardService.boardQuestionRecordEdit(params);
		if(result==1) {
			map.put("message", "게시물이 수정되었습니다.");
			map.put("success", "success");
		}else {
			map.put("message", "게시물 수정 중 에러가 발생했습니다.\n다시시도해주세요");		
		}
		return map;
		
	}
	@DeleteMapping("/question/contentDelete")
	public Map<String,Object> boardQuestionRecordDelete(@RequestBody Map<String,Object> param)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		int seq = (int)param.get("seq");
		int result = 0;
		result = boardService.boardQuestionReplyDelete(seq);
		 if(result!=0) {
			result=boardService.boardQuestionRecordDelete(seq);
			 if(result ==1) {
				 map.put("message","게시물이 삭제되었습니다");
				 map.put("success","success");
			 }else {
				 map.put("message","게시물 삭제 중 에러가 발생했습니다.\n다시시도해주세요");
			 }
		 }else {
			 map.put("message","게시물 삭제 중 에러가 발생했습니다.\n다시시도해주세요");
		 }
		return map;
	}
	
	@GetMapping("/question/reply/{seq}")
	public List<Map<String,Object>> boardQuestionReplyList(@PathVariable int seq) throws Exception {
		List<Map<String,Object>> list = boardService.boardQuestionReplyList(seq);
		return list;
	}
	//댓글 등록 
	@PostMapping("/question/replyInsert/{seq}")
	public Map<String,Object> boardQuestionReplyInsert(@PathVariable int seq ,@RequestBody Map<String,Object> param) throws Exception{
		//Session에 저장되있는 customerNo 가지고 댓글을 등록하는 유저의 이름 아이디 고유번호 가져와서 등록하기
		Map<String,Object> map = new HashMap<String, Object>();
		String customerNo = (String)param.get("customerNo");
		UserVO vo = customerService.userInfo(customerNo);
		param.put("customerNo",vo.getCustomerNo());
		param.put("username",vo.getUsername());
		param.put("email",vo.getEmail());
		//리플 테이블에 게시판 번호 댓글 유저정보등 등록하기
		int result = boardService.boardQuestionReplyInsert(param);
		if(result==1) {
			map.put("message", "댓글이 등록되었습니다.");
			map.put("success", "success");
		}else {
			map.put("message", "댓글이 등록 중 에러가 발생했습니다.\n다시시도해주세요");		
		}
		return map;
	}
	@DeleteMapping("/question/reply/{replyNo}")
	public String boardQuestionReplyDelete(@PathVariable int replyNo) throws Exception {
		boardService.boardQuestionReplyDelete(replyNo);
		String message = "댓글이 삭제되었습니다.";
		return message; 
	}
	
}
