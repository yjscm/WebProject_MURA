package recipe.dao;

import static recipe.db.JdbcUtil.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import recipe.vo.RecipeVO;

public class RecipeDAO {
	Connection con;
	private static RecipeDAO dogDAO;

	public void setConnection(Connection con) {
		this.con = con;
	}

	public static RecipeDAO getInstance() {
		if (dogDAO == null) {
			dogDAO = new RecipeDAO();
		}
		return dogDAO;
	}

	// 이곳부터는 메소드 추가구현.
	public int getArticleCount() {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int x = 0;
		
		try {
			pstmt = con.prepareStatement("select count(*) from food_board");
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				x = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
		return x;
	}
	
	public int getArticleCount(String find, String find_box) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int x = 0;
		
		try {
			if(find.equals("writer")) {
				pstmt = con.prepareStatement("select count(*) from board where writer=?");
				pstmt.setString(1, find_box);
			}else if(find.equals("subject")) {
				pstmt = con.prepareStatement("select count(*) from board where subject like '%" + find_box + "%'");
			}else if(find.equals("content")) {
				pstmt = con.prepareStatement("select count(*) from board where content like '%" + find_box + "%'");
			}else {
				pstmt = con.prepareStatement("select count(*) from board");
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				x = rs.getInt(1);
			}
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
		return x;
	}
	
	// board table에서 가져올 메소드 구현 (List로 구현)
	// 검색할 내용을 리스트로 받아옴(what:검색 조건, content:검색 내용, start:시작번호,end:끝번호)
	public List<RecipeVO> getArticles(int start, int end) {// 수정1
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeVO> articleList = null;
		
		try {
			// 수정2
			//pstmt = con.prepareStatement("select * from board order by num desc");
			pstmt = con.prepareStatement(
					"select * from (select rownum rnum, num, writer, email, subject, pass, "
							+ "regdate, readcount, ref, step, depth, content, ip from "
							+ "(select * from board order by ref desc, step asc)) "
							+ "where rnum >= ? and rnum <= ?");
			
			// 수정3
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
            rs = pstmt.executeQuery();
			
			if(rs.next()) {
				articleList = new ArrayList<RecipeVO>(end - start+1); // 수정4
				do {
					RecipeVO article = new RecipeVO();
					article.setNum(rs.getInt("num"));
					article.setWriter(rs.getString("writer"));
					article.setEmail(rs.getString("email"));
					article.setSubject(rs.getString("subject"));
					article.setPass(rs.getString("pass"));
					article.setRegdate(rs.getTimestamp("regdate"));
					article.setReadcount(rs.getInt("readcount"));
					article.setRef(rs.getInt("ref"));
					article.setStep(rs.getInt("step"));
					article.setDepth(rs.getInt("depth"));
					article.setContent(rs.getString("content"));
					article.setIp(rs.getString("ip"));
					articleList.add(article);
				}while(rs.next());
			}
			
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
		return articleList;
	}
	
	public List<RecipeVO> getArticles(String find, String find_box, int start, int end) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<RecipeVO> articleList = null;
		
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ");
			sql.append("(select rownum rnum, num, writer, email, subject, pass, "
					+ "regdate, readcount, ref, step, depth, content, ip from ");
			
			if(find.equals("writer")) {
				sql.append("(select * from board where writer=? order by ref desc, step asc)) "
						+ "where rnum >= ? and rnum <= ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, find_box);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			}else if(find.equals("subject")) {
				sql.append("(select * from board where subject like '%" + find_box + "%' "
						+ "order by ref desc, step asc)) where rnum >= ? and rnum <= ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
			}else if(find.equals("content")) {
				sql.append("(select * from board where content like '%" + find_box + "%' "
						+ "order by ref desc, step asc)) where rnum >= ? and rnum <= ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
			}else {
				sql.append("(select * from board order by ref desc, step asc)) "
						+ "where rnum >= ? and rnum <= ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, end);
			}
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				articleList = new ArrayList<RecipeVO>(end - start+1); // 수정4
				do {
					RecipeVO article = new RecipeVO();
					article.setNum(rs.getInt("num"));
					article.setWriter(rs.getString("writer"));
					article.setEmail(rs.getString("email"));
					article.setSubject(rs.getString("subject"));
					article.setPass(rs.getString("pass"));
					article.setRegdate(rs.getTimestamp("regdate"));
					article.setReadcount(rs.getInt("readcount"));
					article.setRef(rs.getInt("ref"));
					article.setStep(rs.getInt("step"));
					article.setDepth(rs.getInt("depth"));
					article.setContent(rs.getString("content"));
					article.setIp(rs.getString("ip"));
					articleList.add(article);
				}while(rs.next());
			}
			
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
		return articleList;
	}
	
	// 이곳에 게시판 작업의 기능들을 하나하나 메소드로 추가하면 됨
	public void insertArticle(RecipeVO article) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int number = 0;
		
		String sql = "";
		
		try {
			pstmt = con.prepareStatement("select max(num) from food_board");
			rs = pstmt.executeQuery();
			
			if(rs.next()) number = rs.getInt(1) + 1; // 새글
			else number = 1; // 새글이 아닌 경우
			
			// 새글을 추가하는 쿼리 작성
			sql="insert into food_board(wnum_li, category_li, wsubject_li, igd_li, sumbnail_li, wcontent_li, tag_li) "
					+ "values(board_seq.nextval, ?,?,?,?,?,?,? )";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getCategory_li());
			pstmt.setString(2, article.getWsubject_li());
			pstmt.setString(3, article.getIgd_li());
			pstmt.setString(4, article.getSumbnail_li());
			pstmt.setString(5, article.getWcontent_li());
			pstmt.setString(6, article.getTag_li());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
	}
	
	/* 글 제목을 누르면 글 내용을 볼 수 있도록 해야함
	 * 
	 * 우리는 글 num을 매개변수로 해서 하나의 글에 대한 세부정보를 데이터베이스에 가져와야함
	 * 데이터베이스에서 글 하나의 정보를 가져올 메소드를 구현
	 */
	public RecipeVO getArticle(int num) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeVO article = null;
		
		try {
			pstmt = con.prepareStatement("update board set readcount = readcount+1 where num=?");
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement("select * from board where num=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				article = new RecipeVO();
				article.setNum(rs.getInt("num"));
				article.setWriter(rs.getString("writer"));
				article.setEmail(rs.getString("email"));
				article.setSubject(rs.getString("subject"));
				article.setPass(rs.getString("pass"));
				article.setRegdate(rs.getTimestamp("regdate"));
				article.setReadcount(rs.getInt("readcount"));
				article.setRef(rs.getInt("ref"));
				article.setStep(rs.getInt("step"));
				article.setDepth(rs.getInt("depth"));
				article.setContent(rs.getString("content"));
				article.setIp(rs.getString("ip"));
			}
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
		return article;
	}
	
	/* 글 상세보기 화면에서 [글수정] 버튼을 누를 경우 updateForm.jsp로 이동하도록 링크를 걸었으므로 
	 * 글 수정 화면을 설계해야 함
	 * 
	 * 글 수정시에는 글목록 보기와 다르게 조회수를 증가시킬 필요가 없음
	 * 
	 * 조회수를 증가시키는 부분을 제외하고 num에 해당하는 글을 가져오는 메소드 구현
	 */
	public RecipeVO updateGetArticle(int num) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RecipeVO article = null;
		
		try {
			pstmt = con.prepareStatement("select * from board where num=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				article = new RecipeVO();
				article.setNum(rs.getInt("num"));
				article.setWriter(rs.getString("writer"));
				article.setEmail(rs.getString("email"));
				article.setSubject(rs.getString("subject"));
				article.setPass(rs.getString("pass"));
				article.setRegdate(rs.getTimestamp("regdate"));
				article.setReadcount(rs.getInt("readcount"));
				article.setRef(rs.getInt("ref"));
				article.setStep(rs.getInt("step"));
				article.setDepth(rs.getInt("depth"));
				article.setContent(rs.getString("content"));
				article.setIp(rs.getString("ip"));
			}
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
		return article;
	}
	
	/* 데이터베이스에서 실제 수정 처리가 되어야함
	 * 글을 수정처리 할 메소드 구현
	 * 글이 없을 때 -1, 글 수정 성공 1, 글 수정 실패 : 0
	 */
	public int updateArticle(RecipeVO article) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String dbpasswd = "";
		String sql = "";
		int result = -1;
		
		try {
			pstmt = con.prepareStatement("select pass from board where num=?");
			pstmt.setInt(1, article.getNum());
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbpasswd = rs.getString("pass");
				if(dbpasswd.equals(article.getPass())) {
					// 비밀번호가 일치하면 수정쿼리 실행
					sql = "update board set writer=?, email=?, subject=?, content=? where num=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, article.getWriter());
					pstmt.setString(2, article.getEmail());
					pstmt.setString(3, article.getSubject());
					pstmt.setString(4, article.getContent());
					pstmt.setInt(5, article.getNum());
					pstmt.executeUpdate();
					result = 1; // 수정 성공
				}else {
					result = 0;
				}
			}
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
		return result;
	}
	
	/* 글 삭제 처리
	 * 
	 * 데이터베이스에서 비밀번호를 비교하여 실제로 삭제를 수행해 줄 메소드를 구현함
	 */
	
	public int deleteArticle(int num, String pass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String dbpasswd = "";
		String sql = "";
		int result = -1;
		
		try {
			pstmt = con.prepareStatement("select pass from board where num=?");
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbpasswd = rs.getString("pass");
				if(dbpasswd.equals(pass)) {
					// 비밀번호가 일치하면 수정쿼리 실행
					sql = "delete from board where num=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, num);
					pstmt.executeUpdate();
					result = 1; // 삭제 성공
				}else {
					result = 0; // 비밀번호 틀림
				}
			}
		}catch(Exception e) {
			System.out.println("Exception "+e);
		}finally {
			if(rs != null) try {rs.close();}catch(SQLException s1) {}
			if(pstmt != null) try {pstmt.close();}catch(SQLException s2) {}
			if(con != null) try {con.close();}catch(SQLException s3) {}
		}
		return result;
	}

}