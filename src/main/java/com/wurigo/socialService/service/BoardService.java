package com.wurigo.socialService.service;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.wurigo.socialService.domain.BoardVO;


public interface BoardService {
	
	public List<BoardVO> boardList() throws Exception; 
	public int insertBoard(Map<String,Object> map) throws Exception;
	public BoardVO boardRecordSelect(int seq) throws Exception;
	public int boardRecordDelete(@Param("seq")int seq) throws Exception;
	public int boardRecordUpdate(Map<String,Object> map) throws Exception;

	public List<BoardVO> boardQuestionList() throws Exception;
	public Map<String,Object> boardQuestionRecord(@Param("seq")int seq) throws Exception;
	public int boardQuestionRecordInsert( Map<String,Object> map) throws Exception;
	public int boardQuestionRecordEdit(Map<String,Object> map) throws Exception;
	public int boardQuestionRecordDelete(int seq) throws Exception;
	public List<Map<String,Object>> boardQuestionReplyList(int seq) throws Exception;
	public int boardQuestionReplyInsert( Map<String,Object> map) throws Exception;
	public void boardQuestionReplysDelete(int seq) throws Exception;
	public int boardQuestionReplyDelete(int replyNo) throws Exception;
}

