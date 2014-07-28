package dk.diku.lindsgaard.communication;

import dk.diku.lindsgaard.relation.ActionDispatcher;
import dk.diku.lindsgaard.relation.ActionHandler;
import dk.diku.lindsgaard.utils.Action;
import dk.diku.lindsgaard.utils.Tuple;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/v1")
public class RequestController {

    @RequestMapping(value="/artifact/{action}", method=RequestMethod.POST)
    public void artifactAction(@PathVariable Action action, Artifact artifact, BindingResult bindingResult) {
        //TODO Insert @Valid annotation on artifact

        Tuple<Action, Artifact> foo = new Tuple<Action, Artifact>(action, artifact);
        ActionDispatcher rd = ActionDispatcher.getInstance();


        rd.artifactUpdate(foo);
    }

    @RequestMapping(value="/actionHandler/{name}/{action}", method=RequestMethod.POST)
    public void actionHandlerAction(@PathVariable String name, @PathVariable String Action, BindingResult bindingResult) {


        ActionDispatcher ad = ActionDispatcher.getInstance();
        ad.addObserver(actionHandler);
    }
}