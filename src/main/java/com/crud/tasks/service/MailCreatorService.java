package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyConfig;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyConfig companyConfig;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = getFunctionality();

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("button", "Visit website");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("good_bye", "Created automatically by Task application. (c) Jacek");
        context.setVariable("company_config", companyConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildTaskCountEmail(String message) {

        List<String> functionality = getFunctionality();
        Task lastTask = taskRepository.getLastTask().orElseGet(Task::new);
        Context context = new Context();

        context.setVariable("message", message);
        context.setVariable("button_one", "Visit website");
        context.setVariable("button_two", "Our location");
        context.setVariable("company_config", companyConfig);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_button_one", true);
        context.setVariable("show_button_two", true);
        context.setVariable("is_official", false);
        context.setVariable("last_task", lastTask);
        context.setVariable("application_functionality", functionality);

        return templateEngine.process("mail/task-count-mail", context);
    }

    private List<String> getFunctionality() {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        return functionality;
    }
}
