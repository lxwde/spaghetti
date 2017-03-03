package com.lxwde.spaghetti.archive;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/2/9.
 */
@RestController
@RequestMapping("/api/archive-processing")
public class ArchiveController {

    @RequestMapping(value = "/graph")
    public List<Link> getGraph() {
        MutableGraph<String> graph = GraphBuilder.directed().build();
        graph.putEdge("Device", "DeviceModel");
        graph.putEdge("Vehicle", "Device");
        graph.putEdge("Vehicle", "VehicleBrand");
        graph.putEdge("VehicleBind", "Vehicle");
        graph.putEdge("VehicleBind", "Device");

        List<Link> links = new ArrayList<>();
        for(EndpointPair<String> edge:graph.edges()) {
            Link link = new Link();
            link.setSource(edge.source());
            link.setTarget(edge.target());
            link.setType("suit");

            links.add(link);
        }

        return links;
    }
}
