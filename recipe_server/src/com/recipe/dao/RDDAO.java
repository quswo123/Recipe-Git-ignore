package com.recipe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.recipe.exception.FindException;
import com.recipe.jdbc.MyConnection;
import com.recipe.vo.RD;

public class RDDAO {
	/**
	 * 모든 R&D계정을 조회한다
	 * @return 모든 R&D계정의 정보를 가진 RD객체의 리스트
	 * @throws FindException R&D계정이 하나도 존재하지 않는 경우
	 * @author 최종국
	 */
	public List<RD> selectAll() throws FindException{
		List<RD> result = new ArrayList<RD>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		String selectAllSQL = "SELECT rd_id, rd_pwd, rd_manager_name, rd_team_name, rd_phone FROM rd";
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) result.add(new RD(rs.getString("rd_id"), rs.getString("rd_pwd"), rs.getString("rd_manager_name"), rs.getString("rd_team_name"), rs.getString("rd_phone")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result.isEmpty()) throw new FindException("RD 계정이 하나도 없습니다");
		return result;
	}
	
	/**
	 * 전달받은 아이디를 포함한 R&D 계정의 정보를 가진 RD객체를 반환한다
	 * @param rdId 검색할 아이디
	 * @return R&D계정의 정보를 가진 RD 객체
	 * @throws FindException 매개변수로 전달받은 아이디를 포함한 R&D계정이 존재하지 않는 경우
	 * @author 최종국
	 */
	public RD selectById(String rdId) throws FindException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = MyConnection.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		String selectByIdSQL = "SELECT rd_pwd, rd_manager_name, rd_team_name, rd_phone FROM rd WHERE rd_id = ?";
		try {
			pstmt = con.prepareStatement(selectByIdSQL);
			pstmt.setString(1, rdId);
			rs = pstmt.executeQuery();
			if(rs.next()) return new RD(rdId, rs.getString("rd_pwd"), rs.getString("rd_manager_name"), rs.getString("rd_team_name"), rs.getString("rd_phone"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(rs, pstmt, con);
		}
		throw new FindException("아이디가 존재하지 않습니다");
	}
}
