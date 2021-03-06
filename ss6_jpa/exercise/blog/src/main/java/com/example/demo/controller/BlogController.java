package com.example.demo.controller;

import com.example.demo.model.Blog;
import com.example.demo.service.IBlogService;
import com.example.demo.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    IBlogService iBlogService;

    @Qualifier("postService")
    @Autowired
    IPostService iPostService;


    @GetMapping({"/", "blog"})
    //                       , sort = "id" ,direction = Sort.Direction.DESC
    public String goHome(Model model,
                         @RequestParam(defaultValue = "0") Integer page,
                         @RequestParam(defaultValue = "4") Integer pageSize,
                         @RequestParam(defaultValue = "author") String sort,
                         @RequestParam(defaultValue = "asc") String dir,
                         @RequestParam Optional<String> name) {
        String key = name.orElse("");

        Pageable pageable;

        if (dir.equals("asc")) {
            pageable = PageRequest.of(page, pageSize, Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(page, pageSize, Sort.by(sort).descending());
        }

        model.addAttribute("blogPage", iBlogService.findAllByNameBlogContaining(key, pageable));
        model.addAttribute("keyword",name);
        return "home";
    }

    @GetMapping("/showCreate")
    public String showCreate(Model model, Pageable pageable) {
        model.addAttribute("post", iPostService.findAll(pageable));
        model.addAttribute("blogCreate", new Blog());
        return "create";
    }


    @PostMapping("/create")
    public String createBlog(Blog blog, Model model, RedirectAttributes redirectAttributes) {
        iBlogService.save(blog);
        redirectAttributes.addFlashAttribute("msg", "successfully add new");
            return "redirect:/showCreate";
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model, Pageable pageable) {
        model.addAttribute("postEdit", iPostService.findAll(pageable));
        model.addAttribute("blog", iBlogService.findById(id));
        return "/edit";
    }


    @PostMapping("/update")
    public String edit(@ModelAttribute Blog blog , Model model  , RedirectAttributes redirectAttributes){
        this.iBlogService.update(blog);
        redirectAttributes.addFlashAttribute("update", "successfully update new");
        return "redirect:/blog";
  }


    @GetMapping("/{id}/delete")
    public String delete1(@PathVariable Long id, Model model, Blog blog) {
        Blog blog1 = iBlogService.findById(id);
        iBlogService.delete(blog1);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete2(@RequestParam Long id, Model model, Blog blog) {
        Blog music1 = iBlogService.findById(id);
        iBlogService.delete(music1);
        return "redirect:/";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable Long id, Model model, Pageable pageable) {
        model.addAttribute("postView", iPostService.findAll(pageable));
        model.addAttribute("blog", iBlogService.findById(id));
        return "view";
    }

}
