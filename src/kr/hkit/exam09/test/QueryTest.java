package kr.hkit.exam09.test;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.hkit.exam09.BoardVO;
import kr.hkit.exam09.Query;

class QueryTest {

	@BeforeAll //테스트가 실행되기전 딱한번 실행,얘네는 무조건 static
	static void start() {
		Query.createTable();
	}
	
	@AfterAll //테스트가 끝날때 딱한번 실행,얘네는 무조건 static
	static void end() {
		Query.dropTable();
	}
	
	@BeforeEach //@Test메소드가 실행되기 전마다 항상 실행
	void before() {
		Query.boardDelete(0);
		
		BoardVO bv1 = new BoardVO();
		bv1.setBtitle("타이틀1");
		bv1.setBcontent("내용1");
		Query.boardInsert(bv1);
		
		BoardVO bv2 = new BoardVO();
		bv2.setBtitle("타이틀2");
		bv2.setBcontent("내용2");
		Query.boardInsert(bv2);
	}
	
	@Test
	void testA() {
		List<BoardVO> list = Query.getAllBoardList();		
		Assert.assertEquals(2, list.size());
		
		BoardVO vo1 = list.get(0);
		Assert.assertEquals("타이틀2", vo1.getBtitle());
		Assert.assertEquals("내용2", vo1.getBcontent());
		
		BoardVO vo2 = list.get(1);
		Assert.assertEquals("타이틀1", vo2.getBtitle());
		Assert.assertEquals("내용1", vo2.getBcontent());
	}
	
	@Test
	void testB() {
		List<BoardVO> list = Query.getAllBoardList();
		
		BoardVO vo1 = list.get(0);
		Query.boardDelete(vo1.getBid());
		BoardVO vo1Db = Query.getBoardDetail(vo1.getBid());
		Assert.assertEquals(0, vo1Db.getBid());
		Assert.assertNull(vo1Db.getBtitle());
		Assert.assertNull(vo1Db.getBcontent());
		
		Assert.assertEquals(1, Query.getAllBoardList().size());
		
		//
		BoardVO vo2 = list.get(1);
		Query.boardDelete(vo2.getBid());
		BoardVO vo2Db = Query.getBoardDetail(vo2.getBid());
		Assert.assertEquals(0, vo2Db.getBid());
		Assert.assertNull(vo2Db.getBtitle());
		Assert.assertNull(vo2Db.getBcontent());
		
		Assert.assertEquals(0, Query.getAllBoardList().size());
	}
	
	@Test
	void testC() {
		List<BoardVO> list = Query.getAllBoardList();
		int bid = list.get(0).getBid(); //2
		BoardVO c = new BoardVO();
		c.setBid(bid);
		c.setBtitle("수정 타이틀2");
		c.setBcontent("수정 내용2");
		Query.boardUpdate(c);
		
		
		
		bid = list.get(1).getBid(); //1
		
		BoardVO d = new BoardVO();
		d.setBid(bid);
		d.setBtitle("수정 타이틀1");
		d.setBcontent("수정 내용1");
		Query.boardUpdate(d);
		
		BoardVO cResult = Query.getBoardDetail(c.getBid());
		Assert.assertEquals(c.getBtitle(), cResult.getBtitle());
		Assert.assertEquals(c.getBcontent(),cResult.getBcontent());
		
		BoardVO dResult =  Query.getBoardDetail(d.getBid());
		Assert.assertEquals(d.getBtitle(), dResult.getBtitle());
		Assert.assertEquals(d.getBcontent(), dResult.getBcontent());
	}
	
	/* 
	1 @BeforeAll
	2 @AfterAll //테스트가 끝나고난후 마지막에 실행
	3 @BeforeEach
	4 @AfterEach
	
	@Test - (A)
	@Test - (B)
	@Test - (C)
	
	
		실행순서 
		1 
			3 -> B -> 4
			3 -> C -> 4
			3 -> A -> 4
		2
	*/
	
}
