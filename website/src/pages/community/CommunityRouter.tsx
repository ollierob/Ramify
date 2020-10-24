import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route} from "react-router-dom";
import {Switch} from "react-router";
import ForumsBasePage from "./forums/ForumsBasePage";
import CommunityHomePage from "./CommunityHomePage";

class CommunityRouter extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={CommunityHomePage}/>
                <Route path="/forums" component={ForumsBasePage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<CommunityRouter/>, document.getElementById("main"));