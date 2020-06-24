package com.recipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.recipe.exception.DuplicatedException;
import com.recipe.exception.FindException;
import com.recipe.jdbc.MyConnection;
import com.recipe.vo.Ingredient;
import com.recipe.vo.Point;
import com.recipe.vo.RecipeInfo;
import com.recipe.vo.RecipeIngredient;

public class RecipeInfoDAO {
	public RecipeInfo selectByCode(int recipeCode) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = MyConnection.getConnection();

		} catch (ClassNotFoundException | SQLException e) {

		}
		String selectByCodeSQL = "SELECT RI.RECIPE_CODE, RIN.RECIPE_NAME, RIN.RECIPE_SUMM, RIN.RECIPE_PRICE, RI.ing_code, ING.ING_NAME, RIN.recipe_process, PT.LIKE_COUNT, PT.DISLIKE_COUNT\r\n" + 
				"FROM RECIPE_INGREDIENT RI \r\n" + 
				"LEFT JOIN RECIPE_INFO RIN ON RI.recipe_code = RIN.recipe_code\r\n" + 
				"JOIN INGREDIENT ING ON RI.ing_code = ING.ing_code\r\n" + 
				"left JOIN POINT PT ON RI.RECIPE_CODE = PT.RECIPE_CODE\r\n" + 
				"WHERE RIN.recipe_Code IN\r\n" + 
				"(select recipe_code FROM recipe_info WHERE recipe_code = ?)";
		List<RecipeIngredient> ingList = new ArrayList<>();
		RecipeInfo recipeInfo = new RecipeInfo();
		int prevCode = 0; //
		try {
			pstmt = con.prepareStatement(selectByCodeSQL);
			pstmt.setInt(1, recipeCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int rCode = rs.getInt("recipe_code");
				int ingCode = rs.getInt("ing_code");
				String ingName = rs.getString("ing_name");
				Ingredient ingredient = new Ingredient(ingCode, ingName);
				RecipeIngredient recipeIng = new RecipeIngredient(ingredient);
				//코드랑 이름 값 (Ingredient) recipeIng 에 넣어주고 리스트에 애드해줌
				ingList.add(recipeIng);
				//코드값이 바뀔떄 recipeInfo 에 값 넣어주기
				if (prevCode != rCode) {
					recipeInfo.setRecipeCode(rCode);
					recipeInfo.setRecipeName(rs.getString("recipe_name"));
					recipeInfo.setRecipePrice(rs.getInt("recipe_price"));
					recipeInfo.setRecipeSumm(rs.getString("recipe_summ"));
					recipeInfo.setRecipeProcess(rs.getString("recipe_process"));
					recipeInfo.setIngredients(ingList);
					Point pt = new Point(rCode, rs.getInt("like_count"), rs.getInt("dislike_count"));
					recipeInfo.setPoint(pt);
				}
			}
			if (recipeInfo.getRecipeName() == null) {
				throw new FindException("찾은 레시피가 없습니다");
			}
			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			MyConnection.close(rs, pstmt, con);
		}

		return recipeInfo;
	}
	public List<RecipeInfo> selectByName(String recipeName) throws FindException{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeInfo> recipeInfo = new ArrayList<>();
		try {
			con = MyConnection.getConnection();

		} catch (ClassNotFoundException | SQLException e) {
			throw new FindException(e.getMessage());
		}
		String selectByNameSQL = "SELECT RIN.Recipe_code,RIN.RECIPE_NAME, RIN.RECIPE_SUMM, RIN.RECIPE_PRICE, RI.ing_code, ING.ING_NAME, RIN.recipe_process,PT.LIKE_COUNT, PT.DISLIKE_COUNT\r\n" + 
				"FROM RECIPE_INGREDIENT RI \r\n" + 
				"LEFT JOIN RECIPE_INFO RIN ON RI.recipe_code = RIN.recipe_code\r\n" + 
				"LEFT JOIN INGREDIENT ING ON RI.ing_code = ING.ing_code\r\n" + 
				"LEFT JOIN POINT PT ON RIN.RECIPE_CODE = PT.RECIPE_CODE\r\n" + 
				"WHERE rin.recipe_name LIKE ?";
		try {
			pstmt = con.prepareStatement(selectByNameSQL);
			pstmt.setString(1, "%" + recipeName + "%");
			rs = pstmt.executeQuery();
			List<RecipeIngredient> ingList = null;	
			int prevCode = 0;
			while(rs.next()) {				
				int rCode = rs.getInt("recipe_code");
				//코드값이 바뀔떄 recipeInfo 객체 생성하고 값넣어줌		
				if (prevCode != rCode) {					
					RecipeInfo recipeInfo2 = new RecipeInfo();
					ingList = new ArrayList<>();
					recipeInfo2.setIngredients(ingList);
					recipeInfo2.setRecipeCode(rCode);
					recipeInfo2.setRecipeName(rs.getString("recipe_name"));
					recipeInfo2.setRecipePrice(rs.getInt("recipe_price"));
					recipeInfo2.setRecipeSumm(rs.getString("recipe_summ"));
					recipeInfo2.setRecipeProcess(rs.getString("recipe_process"));
					recipeInfo2.setIngredients(ingList);					
					Point pt = new Point(rCode, rs.getInt("like_count"), rs.getInt("dislike_count"));
					recipeInfo2.setPoint(pt);
					recipeInfo.add(recipeInfo2);
					
					prevCode = rCode;
				}
				
				int ingCode = rs.getInt("ing_code");
				String ingName = rs.getString("ing_name");
				Ingredient ingredient = new Ingredient(ingCode, ingName);
				RecipeIngredient recipeIng = new RecipeIngredient(ingredient);				
				ingList.add(recipeIng);
				
			}
			if (recipeInfo.size() == 0) {
				throw new FindException("찾은 레시피가 없습니다");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MyConnection.close(rs, pstmt, con);
		}
		
		return recipeInfo;
	}
	
	public void insert(RecipeInfo recipe_InfoVo, List<Ingredient> ingList) throws DuplicatedException{
		//입력받아온 recipe_InfoVo,ingList
		Connection conn = null; // DB연결된 상태(세션)을 담은 객체
		PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
		ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체

		String ing_name = "";
		for(Ingredient ingredientVO : ingList) {		//ingList에 있는 객체들을 ingredientVO에 넣으면서 반복문 실행
			ing_name +=", '" + ingredientVO.getIngName() + "'";
		}

		String quary = "SELECT COUNT(1) AS CNT FROM RECIPE_INFO WHERE RECIPE_NAME = ?";
		try {
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			pstm.setString(1,  recipe_InfoVo.getRecipeName());

			rs = pstm.executeQuery();

			int countFlag = 0;
			while(rs.next()) {		//쿼리문을 돌렸을때 받아온 컬럼의 값이 있을때 true
				countFlag = rs.getInt(1);		//있다면 1을 countFlag에 넣는다.
			}
			if(0 < countFlag) {
				throw new DuplicatedException("이미 존재하는 레시피입니다.");
			}
			for(Ingredient ingredientVO : ingList) {		//ingList에 있는 객체들을 ingredientVO에 넣으면서 반복문 실행
				quary = "MERGE INTO INGREDIENT " + 
						"USING DUAL " + 
						"   ON (ING_NAME = ?) " + 
						"WHEN NOT MATCHED THEN " + 
						"    INSERT (ING_CODE, ING_NAME) " + 
						"    VALUES ((SELECT ING_CODE.NEXTVAL FROM DUAL), ?)";		//INGREDIENT테이블에서 ING_NAME을 검색하여 값이 없으면 시퀀스값을 ING_CODE에 넣고, 재료명을 ING_NAME에 넣는다.
				conn = MyConnection.getConnection();
				pstm = conn.prepareStatement(quary);
				pstm.setString(1, ingredientVO.getIngName());
				pstm.setString(2, ingredientVO.getIngName());
				rs = pstm.executeQuery();
			}

			List<Ingredient> ing_codeList = new ArrayList<Ingredient>();
			Ingredient ingredientVo = new Ingredient();

			quary = "SELECT ING_CODE, ING_NAME FROM INGREDIENT WHERE ING_NAME IN ( "
					+ ing_name.substring(1, ing_name.length()) + ")";	//,빼고 ING_CODE와 ING_NAME값을 셀렉트 해오는 쿼리
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			rs = pstm.executeQuery();

			while(rs.next()) {
				ingredientVo = new Ingredient();
				ingredientVo.setIngCode(rs.getInt(1));		//첫번째 값은 재료코드값으로
				ingredientVo.setIngName(rs.getString(2));		//두번째값은 재료이름값으로
				ing_codeList.add(ingredientVo);		//인덱스 하나하나 ing_codeList에 넣어준다.
			}

			quary = "SELECT RECIPE_CODE.NEXTVAL FROM DUAL";		//레시피코드에 시퀀스넘버를 넣어준다.
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			rs = pstm.executeQuery();

			while(rs.next()){
				recipe_InfoVo.setRecipeCode(rs.getInt(1));//setRecipe_code메소드를 이용해서 recipe_InfoVo의 Recipe_code에 넣어준다
			}
			recipe_InfoVo.setRecipeProcess("/Users/Shared/recipeProcess/" + recipe_InfoVo.getRecipeCode() + ".txt");		//파일생성.

			quary = "INSERT INTO RECIPE_INFO VALUES(?, ?, ?, ?, ?, ?, ?)";		//RECIPE_INFO 에 값들을 넣어주는 쿼리문
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			pstm.setInt(1, recipe_InfoVo.getRecipeCode());
			pstm.setString(2, recipe_InfoVo.getRecipeName());
			pstm.setString(3, recipe_InfoVo.getRecipeSumm());
			pstm.setDouble(4, recipe_InfoVo.getRecipePrice());
			pstm.setString(5, recipe_InfoVo.getRecipeProcess());
			pstm.setString(6, ("1"));
			pstm.setString(7, "RD ID 받아오기");

			rs = pstm.executeQuery();

			String fileOutputMessage = "";
			for(Ingredient ingredientVO2 : ingList) {
		//		fileOutputMessage += ingredientVO2.getIngName() + " " + ingredientVO2.getIngCpcty() + " ";		//재료명과 용량을 fileOutputMessage에 넣어준다.
			}
			fileOutputMessage += "\n" + recipe_InfoVo.getRecipeSumm();		//한칸 넘겨서 요리설명을 넣어준다.

		//	new FileService().FileOutput(recipe_InfoVo.getRecipeProcess(), fileOutputMessage);		//파일에 데이터넣는 메소드 호출

			quary = "INSERT INTO POINT VALUES(?, 0, 0)";
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			pstm.setInt(1, recipe_InfoVo.getRecipeCode());

			rs = pstm.executeQuery();

			for(Ingredient ingredientVO : ing_codeList) {			//ing_codeList에 있는것들을 ingredientVO에 넣으면서 반복문 돌림.
				quary = "INSERT INTO RECIPE_INGREDIENT VALUES (?, ?, ?)";		//리세피코드, 재료코드, 용량 insert 해주는 쿼리
				conn = MyConnection.getConnection();
				pstm = conn.prepareStatement(quary);
				pstm.setInt(1, recipe_InfoVo.getRecipeCode());
				pstm.setInt(2,  ingredientVO.getIngCode());
				for(Ingredient ingredientVO2 : ingList) {
					if(ingredientVO.getIngName().equals(ingredientVO2.getIngName())) {
	//					pstm.setString(3, ingredientVO2.getIngCpcty());
						break;
					}
				}
				rs = pstm.executeQuery();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			// DB 연결을 종료한다.
			try{
				if ( rs != null ){rs.close();}   
				if ( pstm != null ){pstm.close();}   
				if ( conn != null ){conn.close();}
			}catch(Exception e){
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	public void update(RecipeInfo recipe_InfoVo, List<Ingredient> ingList) {
		//입력받아온 recipe_InfoVo,ingList
		Connection conn = null; // DB연결된 상태(세션)을 담은 객체
		PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
		ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체

		String quary = "DELETE FROM RECIPE_INFO WHERE RECIP_NAME = ?";
		try {
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			pstm.setString(1, recipe_InfoVo.getRecipeName());

			rs = pstm.executeQuery();

			//insert문 불러오기
			
		/*
		 * -------------
			-- 레시피 수정
			-------------
			-- SELECT * FROM RECIPE_INFO WHERE RECIPE_NAME = '돈까스';
			-- RECIPE_CODE : 49
			
			-- 1. 레시피 재료 데이터 삭제 (수정하려는 레시피 코드 기준으로)
			-- SELECT * FROM RECIPE_INGREDIENT WHERE RECIPE_CODE = 49;
			DELETE FROM RECIPE_INGREDIENT WHERE RECIPE_CODE = 49;
			
			-- 2. 레시피 내용 UPDATE
			UPDATE RECIPE_INFO SET 업데이트~ WHERE RECIPE_NAME = '돈까스';
			
			-- 3. 재료 등록
			
			-- 3-1. 레시피 재료 정보를 INSERT 한다.
			SELECT ING_CODE FROM INGREDIENT WHERE ING_NAME = '새우살';
			MERGE INTO INGREDIENT 
			USING DUAL
			   ON (ING_NAME = '새우살')
			WHEN NOT MATCHED THEN
			    INSERT (ING_CODE, ING_NAME) 
			    VALUES ((SELECT MAX(ING_CODE) + 1 FROM INGREDIENT), '새우살');
			
			-- 3-2. 재료 코드 리스트를 조회한다.
			SELECT ING_CODE FROM INGREDIENT WHERE ING_NAME IN ('돼지고기','빵가루','계란','밀가루');
			-> 16,26,65,178
			
			-- 3-3. 재료코드 리스트 반복문을 수행하면서 (레시피코드 - 재료코드) 레시피 재료테이블에 등록한다.
			for ( ING_CODE list )
			INSERT INTO RECIPE_INGREDIENT VALUES (49, 16);
			INSERT INTO RECIPE_INGREDIENT VALUES (49, 26);
			INSERT INTO RECIPE_INGREDIENT VALUES (49, 65);
			INSERT INTO RECIPE_INGREDIENT VALUES (49, 178);
			
			
			
			
		-------------
		-- 레시피 삭제
		-------------
		SELECT * FROM RECIPE_INFO WHERE RECIPE_NAME = '돈까스';
		RECIPE_CODE : 49

		-- 1. 레시피 재료 데이터 삭제 (수정하려는 레시피 코드 기준으로)
		DELETE FROM RECIPE_INGREDIENT WHERE RECIPE_CODE = 49;

		-- 2. 레시피 삭제
		DELETE FROM RECIPE_INFO WHERE RECIPE_CODE = 49;
		 */
			//레시피코드값 구해오는것은 맨위에서 받아온것으로 사용
			quary = "DELETE FROM RECIPE_INGREDIENT WHERE RECIPE_CODE = ?";
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			
			
			quary = "DELETE FROM RECIPE_INFO WHERE RECIPE_CODE = ?";
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally{
			// DB 연결을 종료한다.
			try{
				if ( rs != null ){rs.close();}   
				if ( pstm != null ){pstm.close();}   
				if ( conn != null ){conn.close(); }
			}catch(Exception e){
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	public String selectAll() {
		Connection conn = null; // DB연결된 상태(세션)을 담은 객체
		PreparedStatement pstm = null;  // SQL 문을 나타내는 객체
		ResultSet rs = null;  // 쿼리문을 날린것에 대한 반환값을 담을 객체

		String quary = "SELECT RECIPE_CODE, RECIPE_NAME FROM RECIPE_INFO";
		try {
			conn = MyConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		-------------
		-- 레시피 목록보기
		-------------
		SELECT recipe_code 코드번호, recipe_name 레시피명 FROM RECIPE_INFO;
		 */
		return null;
	}

	
	public static void main(String[] args) {
		RecipeInfoDAO dao = new RecipeInfoDAO();
//		int code = 195453;
//		try {
//			RecipeInfo list = dao.selectByCode(code);
//			System.out.println("code:" + list.getRecipeCode() + "  name:" + list.getRecipeName() + "  summ:"+ list.getRecipeSumm() +"  price:"+ list.getRecipePrice());
//			List<RecipeIngredient> lines = list.getIngredients();
//			Point pt = list.getPoint();
//			System.out.println("like" + pt.getLikeCount() + ":"+ "dislike" + pt.getDisLikeCount());
//			for(RecipeIngredient line : lines) {
//				System.out.print(line.getIngredient().getIngCode() + ":");
//				System.out.print(line.getIngredient().getIngName()+ ", ");
//			}
//		} catch (FindException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		String name = "단호박";
	
		try {
			List<RecipeInfo> list2 = dao.selectByName(name);
			for(RecipeInfo ri : list2) {
				System.out.println(ri.getRecipeCode() + ri.getRecipeName() + ri.getRecipePrice() + ri.getRecipeProcess() + ri.getRecipeSumm());
				List<RecipeIngredient> lines = ri.getIngredients();
				for(RecipeIngredient ing : lines) {
					System.out.println(ing.getIngredient().getIngName());
				}
			}
		} catch (FindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	/**
	 * 좋아요 개수(내림차순), 싫어요 개수(오름차순), 작성된 후기 개수(내림차순)를 기준으로 추천 레시피를 선정하여 반환한다 
	 * @return 추천 레시피 정보를 포함한 RecipeInfo 객체
	 * @throws FindException
	 */
	public RecipeInfo selectByRank() throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new FindException(e.getMessage());
		}
		
		String selectByRankSQL = "SELECT ri.recipe_code, ri.recipe_name, ri.recipe_summ, ri.recipe_price, ri.recipe_process, po.like_count, po.dislike_count\r\n" + 
				"FROM recipe_info ri JOIN point po ON (ri.recipe_code = po.recipe_code)\r\n" + 
				"WHERE\r\n" + 
				"    ri.recipe_code = (\r\n" + 
				"        SELECT\r\n" + 
				"            recipe_code\r\n" + 
				"        FROM (\r\n" + 
				"                SELECT\r\n" + 
				"                    recipe_code\r\n" + 
				"                FROM\r\n" + 
				"                    point p\r\n" + 
				"                ORDER BY\r\n" + 
				"                    like_count DESC,\r\n" + 
				"                    dislike_count ASC, (\r\n" + 
				"                        SELECT\r\n" + 
				"                            COUNT(*)\r\n" + 
				"                        FROM\r\n" + 
				"                            review\r\n" + 
				"                        WHERE\r\n" + 
				"                            recipe_code = p.recipe_code\r\n" + 
				"                    ) DESC\r\n" + 
				"            )\r\n" + 
				"        WHERE ROWNUM = 1)";
		
		try {
			pstmt = con.prepareStatement(selectByRankSQL);
			rs = pstmt.executeQuery();
			
			if(rs.next()) return new RecipeInfo(rs.getInt("recipe_code"), rs.getString("recipe_name"), rs.getString("recipe_summ"), rs.getDouble("recipe_price"), rs.getString("recipe_process"), new Point(rs.getInt("recipe_code"), rs.getInt("like_count"), rs.getInt("dislike_count")), null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		throw new FindException("추천 레시피 탐색 오류");
	}
}
