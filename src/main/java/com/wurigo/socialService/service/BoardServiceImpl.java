package com.wurigo.socialService.service;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wurigo.socialService.dao.BoardDAO;
import com.wurigo.socialService.domain.BoardVO;
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardDAO mapper;
	
	@Override
	public List<BoardVO> boardList() throws Exception {
		return mapper.boardList();
	}
	@Override
	public int insertBoard(Map<String,Object> map) throws Exception {
		return mapper.insertBoard(map);
	}
	@Override
	public BoardVO boardRecordSelect(int seq) throws Exception {
		return mapper.boardRecordSelect(seq);
	}
	@Override
	public int boardRecordDelete(int seq) throws Exception {

		return mapper.boardRecordDelete(seq);
		
	}
	@Override
	public int boardRecordUpdate(Map<String,Object> map) throws Exception {
		return mapper.boardRecordUpdate(map);
		
	}
	@Override
	public List<BoardVO> boardQuestionList() throws Exception {
		return mapper.boardQuestionList();
	}
	@Override
	public Map<String,Object>  boardQuestionRecord(int seq) throws Exception {
		return mapper.boardQuestionRecord(seq);
	}
	@Override
	public int boardQuestionRecordDelete(int seq) throws Exception {
		
		return mapper.boardQuestionRecordDelete(seq);
		
	}
	@Override
	public int boardQuestionRecordEdit(Map<String,Object> map) throws Exception {
		 return mapper.boardQuestionRecordEdit(map);
	}
	@Override
	public int boardQuestionRecordInsert(Map<String,Object> map) throws Exception {
			 return mapper.boardQuestionRecordInsert(map);		
	}
	@Override
	public List<Map<String, Object>> boardQuestionReplyList(int seq) throws Exception {
		// TODO Auto-generated method stub
		return mapper.boardQuestionReplyList(seq);
	}

	@Override
	public int boardQuestionReplyInsert(Map<String, Object> map) throws Exception {
			return mapper.boardQuestionReplyInsert(map);
	}
	@Override
	public void boardQuestionReplysDelete(int seq) throws Exception {
			mapper.boardQuestionReplysDelete(seq);
		
	}
	@Override
	public int boardQuestionReplyDelete(int replyNo) throws Exception {
		return mapper.boardQuestionReplyDelete(replyNo);
		
	}

}
