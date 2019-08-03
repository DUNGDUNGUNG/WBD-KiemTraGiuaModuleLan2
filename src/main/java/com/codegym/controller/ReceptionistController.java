package com.codegym.controller;

import com.codegym.model.Receptionist;
import com.codegym.model.ReceptionistForm;
import com.codegym.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/receptionists")
@PropertySource("classpath:global_config_app.properties")
public class ReceptionistController {

    @Autowired
    Environment env;


    @Autowired
    private ReceptionistService receptionistService;

    @GetMapping("/list")
    public ModelAndView listReceptionist() {
        return new ModelAndView("list", "receptionists", receptionistService.findAll());
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        return new ModelAndView("create", "receptionistForm", new ReceptionistForm());
    }

    @PostMapping("/save")
    public ModelAndView saveReceptionist(@ModelAttribute ReceptionistForm receptionistForm, RedirectAttributes redirectAttributes) {
        // lay ten file
        MultipartFile multipartFile = receptionistForm.getAvatar();

        String fileUpload = env.getProperty("file_upload").toString();
        String fileName = multipartFile.getOriginalFilename();

        // luu file len server
        try {
            FileCopyUtils.copy(receptionistForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // tham khảo: https://github.com/codegym-vn/spring-static-resources

        //tao doi tuong de luu vao db
        Receptionist receptionistObject = new Receptionist(receptionistForm.getId(), receptionistForm.getName(), receptionistForm.getAge(), receptionistForm.getAddress(), receptionistForm.getHobby(), fileName);

        //luu vao db
        receptionistForm.setId((int) (Math.random() * 5555));
        receptionistService.save(receptionistObject);
        redirectAttributes.addFlashAttribute("success", "Saved receptionist successfully!");
        return new ModelAndView("redirect:/receptionists/list");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditForm(@PathVariable int id) {
        Receptionist receptionist = receptionistService.findById(id);
        ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(), receptionist.getAge(), receptionist.getAddress(), receptionist.getHobby(), null);
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("receptionistForm", receptionistForm);
        modelAndView.addObject("receptionist", receptionist);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateReceptionist(RedirectAttributes redirectAttributes, @ModelAttribute ReceptionistForm receptionistForm) {

        MultipartFile multipartFile = receptionistForm.getAvatar();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();

        try {
            FileCopyUtils.copy(receptionistForm.getAvatar().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //tao doi tuong de luu vao db
        Receptionist receptionistObject = new Receptionist(receptionistForm.getId(), receptionistForm.getName(), receptionistForm.getAge(), receptionistForm.getAddress(), receptionistForm.getHobby(), fileName);

        //luu lai vao db
        receptionistService.update(receptionistForm.getId(), receptionistObject);

        redirectAttributes.addFlashAttribute("success", "Modified receptionist successfully!");
        return new ModelAndView("redirect:/receptionists/list");
    }

    @GetMapping("/{id}/delete")
    public ModelAndView showDeleteForm(@PathVariable int id) {
        Receptionist receptionist = receptionistService.findById(id);
        ReceptionistForm receptionistForm = new ReceptionistForm(receptionist.getId(), receptionist.getName(), receptionist.getAge(), receptionist.getAddress(), receptionist.getHobby(), null);
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("receptionist", receptionist);
        modelAndView.addObject("receptionistForm", receptionistForm);
        return modelAndView;
    }

    @PostMapping("/remove")
    public ModelAndView removeReceptionist(RedirectAttributes redirectAttributes,@ModelAttribute ReceptionistForm receptionistForm) {
        receptionistService.remove(receptionistForm.getId());
        redirectAttributes.addFlashAttribute("success", "Removed receptionist successfully!");
        return new ModelAndView("redirect:/receptionists/list");
    }

    @GetMapping("/{id}/view")
    public ModelAndView viewReceptionist(@PathVariable int id) {
        return new ModelAndView("view", "receptionists", receptionistService.findById(id));
    }

    @PostMapping("/search")
    public ModelAndView searchNameReceptionist(RedirectAttributes redirectAttributes, @RequestParam("search") String searchName) {
        redirectAttributes.addFlashAttribute("success", "Search Name receptionist successfully!");
        return new ModelAndView("search", "receptionists", receptionistService.findByName(searchName));
    }
}
