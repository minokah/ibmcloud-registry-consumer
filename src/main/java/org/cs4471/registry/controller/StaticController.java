package org.cs4471.registry.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.cs4471.registry.service.ServiceEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;

@Controller
public class StaticController {
    @Value("${service.registrar}")
    private String registrarURL;

    @GetMapping("/")
    public String root(Model model, HttpServletRequest request) {
        String nameFilter = request.getParameter("name");
        String descFilter = request.getParameter("desc");

        if (nameFilter == null) nameFilter = "";
        if (descFilter == null) descFilter = "";

        String query = String.format("/getServices?name=%s&desc=%s", nameFilter, descFilter);

        ArrayList<ServiceEntry> entries = WebClient.builder().baseUrl(registrarURL).build().get().uri(query).retrieve().bodyToMono(ArrayList.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(Exception.class, ex -> Mono.just(new ArrayList()))
                .block();

        model.addAttribute("entries", entries);
        return "listing";
    }
}
