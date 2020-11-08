import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route} from "react-router-dom";
import {Switch} from "react-router";
import CommunityHomePage from "./CommunityHomePage";
import ViewForumPage from "./forums/ViewForumPage";

class CommunityRouter extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={CommunityHomePage}/>
                <Route path="/forum" component={ViewForumPage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<CommunityRouter/>, document.getElementById("main"));