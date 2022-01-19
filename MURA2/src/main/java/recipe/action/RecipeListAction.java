package recipe.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.CommandAction;
import recipe.model.RecipeDAO;
import recipe.model.RecipeVO;

public class RecipeListAction implements CommandAction {

	// 글목록 처리
	@Override
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
		
		String pageNum = request.getParameter("pageNum"); // 페이지 번호
		
		if(pageNum == null) {
			pageNum = "1";
		}
		
		int pageSize = 5; // 한 페이지의 글의 개수
		int currentPage = Integer.parseInt(pageNum);
		
		// 한 페이지의 시작 글 번호
		int startRow = (currentPage - 1) * pageSize + 1;
		
		// 한 페이지의 마지막 글 번호
		int endRow = currentPage * pageSize;
		
		int count = 0;
		int number = 0;
		
		// 검색
		String find = null;
		String find_box = null;
		
		find = request.getParameter("find");
		find_box = request.getParameter("find_box");
		
		if(find == null) {
			find = "no";
		}
		
		if(find_box == null) {
			find_box = "no";
		}
		
		List<RecipeVO> articleList = null;
		
		// 데이터베이스 연동
		RecipeDAO dbPro = RecipeDAO.getInstance();
		
		count = dbPro.getArticleCount(find, find_box);
		if(count > 0) {
			articleList = dbPro.getArticles(find, find_box, startRow, endRow);
		}else {
			articleList = Collections.emptyList();
		}
		
		// 글 목록에 표시할 글번호를 의미함
		number = count - (currentPage - 1) * pageSize;
		
		// 해당 뷰에서 사용할 속성 저장
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startRow", startRow);
		request.setAttribute("endRow", endRow);
		request.setAttribute("count", count);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("number", number);
		request.setAttribute("articleList", articleList);
		request.setAttribute("find", find);
		request.setAttribute("find_box", find_box);
		
		// 오늘 본 레시피 쿠키 처리
		ArrayList<String> todayRecipeList = new ArrayList<String>();
		
		Cookie[] cookieArray = request.getCookies();
		
		if(cookieArray != null) {
			
			for(int i = 0; i < cookieArray.length; i++) {
				if(cookieArray[i].getName().startsWith("today")) {
					todayRecipeList.add(cookieArray[i].getValue());
				}
			}
		}
		request.setAttribute("todayImageList", todayRecipeList);
		
		return "/page/recipe/recipeList.jsp";
	}
	
}
