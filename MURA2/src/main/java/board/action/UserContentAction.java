package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommandAction;
import board.dao.UserBoardDAO;
import board.vo.UserBoard;

public class UserContentAction implements CommandAction {

	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		int idx_ut = Integer.parseInt(request.getParameter("idx_ut"));
		String pageNum = request.getParameter("pageNum");
		
		UserBoardDAO dbPro = UserBoardDAO.getInstance();
		
		UserBoard userArticle = dbPro.getUserArticle(idx_ut);
		
		request.setAttribute("idx_ut", idx_ut);
		request.setAttribute("pageNum", new Integer(pageNum));
		request.setAttribute("userArticle", userArticle);
		
		return "/userboard/userContent.jsp";
	}

}