package member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {

	private static MemberDAO instance = null;

	public MemberDAO() {
		// TODO Auto-generated constructor stub
	}

	// DB ����
	public static MemberDAO getInstance() {

		if (instance == null) {
			synchronized (MemberDAO.class) {
				instance = new MemberDAO();
			}
		}
		return instance;
	}

	public Connection getConnection() {

		Connection con = null;

		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
			con = ds.getConnection();
		} catch (Exception e) {
			System.out.println("��� ���� ����");
		}
		return con;
	}

	// id, pass ������ 1, pass�� Ʋ���� 0, id ������ -1 (���� �ȸ���)
	public int loginCheck(String id_mem, String pw_mem) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = -1; // (������� ������ �����ϱ�) ���̵� ����
		
		try {

			con = getConnection();
			String sql = "select pw_mem from member_board where id_mem=?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id_mem);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				if (rs.getString(1).equals(pw_mem)) {
					result = 1; // �α��� ����
					System.out.println("db���Ἲ��, �α��μ���");
				} else {
					result = 0; // ��й�ȣ ����ġ
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (Exception ee) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ee) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ee) {
				}
		}

		return result;
	}

	// ȸ�����Խ� ȸ������ ���� �Է�
	public boolean memberInsert(MemberVO vo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean result = false;

		try {
			con = getConnection();
			String sql = "insert into member_board values(content_seq.nextval,default,?,?,?,?,?,?,?,?,?,?,'Y',sysdate)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, vo.getId_mem());
			pstmt.setString(2, vo.getNn_mem());
			pstmt.setString(3, vo.getPw_mem());
			pstmt.setString(4, vo.getName_mem());
			pstmt.setString(5, vo.getEmail_mem());
			pstmt.setString(6, vo.getGender_mem());
			pstmt.setString(7, vo.getTel_mem());
			pstmt.setString(8, vo.getZipcode_mem());
			pstmt.setString(9, vo.getZc1_mem());
			pstmt.setString(10, vo.getZc2_mem());

			int count = pstmt.executeUpdate();

			if (count > 0)
				result = true; // ������ ������ �ż� true=�������� ��ȯ

		} catch (Exception e) {
			System.out.println("Exception" + e);
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException se) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException se) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException se) {
				}
		}
		return result;

	}

	// ȸ�����Խ� id �ߺ�Ȯ�� �޼ҵ�
	public boolean idCheck(String id_mem) {

		boolean result = true; // ���̵� �ߺ�

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = getConnection();
			String sql = "select * from member_board where id_mem=?";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, id_mem);

			rs = pstmt.executeQuery();

			if (!rs.next())
				result = false; // ���̵� �ߺ� �ƴ�

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException se) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException se) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException se) {
				}
		}

		return result;
	}

	// ������ȣ�� ������ ���̽����� �˻��ؼ� Vector�� ��Ƽ� �������ִ� ����� DAO�� �߰�

	public Vector<ZipcodeVO> zipcodeRead(String dong) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Vector<ZipcodeVO> vecList = new Vector<ZipcodeVO>();

		try {

			String sql = "select * from zipcode where dong like '" + dong + "%'";
			con = getConnection();

			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				ZipcodeVO tempZipcode = new ZipcodeVO();

				tempZipcode.setZipcode(rs.getString("zipcode"));
				tempZipcode.setSido(rs.getString("sido"));
				tempZipcode.setGugun(rs.getString("gugun"));
				tempZipcode.setDong(rs.getString("dong"));
				tempZipcode.setRi(rs.getString("ri"));
				tempZipcode.setBunji(rs.getString("bunji"));

				// ���Ϳ� �߰�
				vecList.addElement(tempZipcode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException se) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException se) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException se) {
				}
		}
		return vecList;
	}
	
	// ���̵� ã�� �޼ҵ�
	public String findId(String name_mem, String email_mem) {
		
		Connection con = null;
		PreparedStatement pstmt =  null;
		ResultSet rs = null;
		String id_mem = null;
		
		try {
			
			con = getConnection();
			String sql = "select id_mem from member_board where name_mem=? and email_mem=?";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, name_mem);
			pstmt.setString(2, email_mem);
			
			rs = pstmt.executeQuery();

			if(rs.next()) id_mem = rs.getString("id_mem");	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException se) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException se) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException se) {
				}
		}
		return id_mem;
	}
	
	// ��й�ȣ ã�� �޼ҵ�
public String findPw(String name_mem, String id_mem, String email_mem) {
		
		Connection con = null;
		PreparedStatement pstmt =  null;
		ResultSet rs = null;
		String pw_mem = null;
		
		try {
			
			con = getConnection();
			String sql = "select pw_mem from member_board where name_mem=? and id_mem=? and email_mem=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name_mem);
			pstmt.setString(2, id_mem);
			pstmt.setString(3, email_mem);
			
			rs = pstmt.executeQuery();

			
			if(rs.next()) pw_mem = rs.getString("pw_mem");
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (SQLException se) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException se) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException se) {
				}
		}
		return pw_mem;
	}
	

}