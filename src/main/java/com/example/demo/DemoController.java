package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class DemoController {

	@Autowired
	PostsRepository repos;
	
	//	一覧画面へ遷移
	@GetMapping("/")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		List<Posts> list = repos.findAll();
		mav.setViewName("list");
		mav.addObject("data", list);
		return mav;
	}
	
	
	//	新規作成画面へ遷移
	@PostMapping("/insert")
	public ModelAndView insert() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("insert");
		return mav;
	}
	
	
	//	編集画面へ遷移
	@PostMapping("/edit")
	public ModelAndView edit(@RequestParam int id) {
		ModelAndView mav = new ModelAndView();
		Posts data = repos.findById(id);
		mav.addObject("formModel", data);
		mav.setViewName("edit");
		return mav;
	}
	
	
	//	新規作成/更新実行
	@PostMapping("/save")
	@Transactional(readOnly=false)
	public ModelAndView save(@ModelAttribute("formModel") Posts posts) {
		repos.saveAndFlush(posts);
		return new ModelAndView("redirect:/");
	}
	
	
	//	削除処理実行
	@PostMapping("/delete")
	@Transactional(readOnly=false)
	public ModelAndView delete(@RequestParam int id) {
		repos.deleteById(id);
		return new ModelAndView("redirect:/");
	}
	
	//	初期データ作成
	@PostConstruct
	public void init() {
		Posts post1 = new Posts();
		post1.setTitle("コロナが流行しています。");
		post1.setContent("コロナなので、10/15以降は休校とします。");
		post1.setAuthor("校長");
		repos.saveAndFlush(post1);

		Posts post2 = new Posts();
		post2.setTitle("台風に気をつけ！");
		post2.setContent("台風や");
		post2.setAuthor("わしじゃ");
		repos.saveAndFlush(post2);
	}
}
