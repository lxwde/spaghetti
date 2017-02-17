package com.lxwde.spaghetti.archive;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/2/9.
 */
@RestController
@RequestMapping("/api/archive-processing")
public class ArchiveController {

    @RequestMapping(value = "/graph")
    public String getGraph() {
        MutableGraph<String> graph = GraphBuilder.directed().build();
        graph.putEdge("User", "Blog");
        graph.putEdge("Post", "Blog");
        graph.addNode("Item");

        return "";
    }
}
