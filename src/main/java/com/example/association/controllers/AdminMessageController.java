//package com.example.association.controllers;
//
//import com.example.association.models.ContactMessage;
//import com.example.association.repositories.ContactMessageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin/messages")
//public class AdminMessageController {
//
//    @Autowired
//    private ContactMessageRepository contactMessageRepository;
//
//    @GetMapping
//    public String listMessages(@RequestParam(required = false) String query, Model model) {
//        List<ContactMessage> messages;
//        if (query != null && !query.isEmpty()) {
//            messages = contactMessageRepository.findByNomContainingIgnoreCaseOrSujetContainingIgnoreCase(query, query);
//        } else {
//            messages = contactMessageRepository.findAll();
//        }
//        model.addAttribute("messages", messages);
//        model.addAttribute("query", query);
//        return "admin_messages";
//    }
//}

package com.example.association.controllers;

import com.example.association.models.ContactMessage;
import com.example.association.repositories.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;

@Controller
@RequestMapping("/admin/messages")
public class AdminMessageController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @GetMapping
    public String listMessages(@RequestParam(required = false) String query,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "nom") String sortBy,
                               @RequestParam(defaultValue = "asc") String direction,
                               Model model) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, 10, sort);

        Page<ContactMessage> messages;
        if (query != null && !query.isEmpty()) {
            messages = contactMessageRepository.findByNomContainingIgnoreCaseOrSujetContainingIgnoreCase(query, query, pageable);
        } else {
            messages = contactMessageRepository.findAll(pageable);
        }

        model.addAttribute("messages", messages);
        model.addAttribute("query", query);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        return "admin_messages";
    }
}
