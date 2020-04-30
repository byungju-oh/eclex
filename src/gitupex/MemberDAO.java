package gitupex;

import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import meatchain.UserBean;
import gitupex.MemberVO;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List listMembers(MemberVO memberVO) {
		List membersList = new ArrayList();
		String _id=memberVO.getId();
		try {
			con = dataFactory.getConnection();
			String query = "select * from block ";
			
			if((_id!=null && _id.length()!=0)){
				 query+=" where id=?";
				 pstmt = con.prepareStatement(query);
				 pstmt.setString(1, _id);
			}else {
				pstmt = con.prepareStatement(query);	
				pstmt.setString(1,null);
			}
			
			
			System.out.println("prepareStatememt: " + query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String pigwh = rs.getString("pigwh");
				String pigti = rs.getString("pigti");
				String pigle = rs.getString("pigle");
				String pigse = rs.getString("pigse");
				MemberVO vo = new MemberVO();
				vo.setId(id);
				vo.setPigwh(pigwh);
				vo.setPigti(pigti);
				vo.setPigle(pigle);
				vo.setPigse(pigse);
				membersList.add(vo);
			}
			rs.close();
			pstmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return membersList;
	}
	public void addMember(MemberVO memberVO) {
		try {
			con = dataFactory.getConnection();
			String id = memberVO.getId();
			String pigwh = memberVO.getPigwh();
			String pigti = memberVO.getPigti();
			String pigle = memberVO.getPigle();
			String pigse = memberVO.getPigse();
			String query = "insert into block";
			query += " (id,pigwh,pigti,pigle,pigse)";
			query += " values(?,?,?,?,?)";
			System.out.println("prepareStatememt: " + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pigwh);
			pstmt.setString(3, pigti);
			pstmt.setString(4, pigle);
			pstmt.setString(5, pigse);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void delMember(String id) {
		try {
			con = dataFactory.getConnection();
			String query = "delete from block" + " where id=?";
			System.out.println("prepareStatememt:" + query);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
