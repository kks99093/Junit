package kr.hkit.exam09.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import kr.hkit.exam09.Query;

class ConnectionTest {

	@Test
	void test() {
		assertNotNull(Query.getConn());
	}

}
