2016-2학기 GUI 최종 발표용.

[내용]

DBConnector 이름과 같이 DB와 연결해주는 클래스입니다.

만약 서버와의 연결이 끊어지거나 이상이 생기게 되면 에러창을 띄우게 합니다.


[사용방법]

	[Select문 사용 시]

		DBConnector db = new DBConnector();

		String sql = "SELECT ID FROM 아이템";

		db.query( sql );		// select 문장 실행하는 함수

		while ( db.querycheck() ) {		// select 결과가 있는지 확인(없을 때 까지 반복)
			String[] tmp = new String[ 칼럼 개수 ];	// 받아온 걸 임시 저장할 곳
		
			for ( int i = 0; i < 칼럼 개수; i++ ) {
				tmp[ i ] = db.getquery( i + 1 );	// 진짜로 받아와서 저장
			}
		
			//저장된 데이터 처리
		}
		
		db.close();
		

	[Insert, Update, Delete문 사용 시]
		
		DBConnector db = new DBConnector();
		
		String sql = "UPDATE 아이템 SET ID = " + newid;	// 아이템 테이블의 ID를 newid로 바꿈
		
		db.query2( sql );		// Select 문 빼고 모두는 query2() 숫자 2가 붙는 함수임.
		
		db.close();