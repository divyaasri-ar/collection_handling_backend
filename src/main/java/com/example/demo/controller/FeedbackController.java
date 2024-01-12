package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.FeedbackService;
import com.example.demo.model.Feedback;

@RestController
@RequestMapping("/api")
public class FeedbackController {
	private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/feedback")
    public void saveFeedback(@RequestBody Feedback feedback) {
        feedbackService.saveFeedback(feedback);
    }

}
