import * as ReactDOM from "react-dom";
import * as React from "react";
import {Route, Switch} from "react-router";
import {HashRouter} from "react-router-dom";
import PeopleSearchPage from "./search/PeopleSearchPage";
import {CreateTreePage} from "./trees/CreateTreePage";
import PersonProfilePage from "./profile/PersonProfilePage";
import ViewTreePage from "./trees/ViewTreePage";
import EditTreePage from "./trees/EditTreePage";
import ViewTreePeoplePage from "./trees/ViewTreePeoplePage";

class PeopleRouter extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={PeopleSearchPage}/>
                <Route path="/search" component={PeopleSearchPage}/>
                <Route path="/tree/create" component={CreateTreePage}/>
                <Route path="/tree/view" component={ViewTreePage}/>
                <Route path="/tree/edit" component={EditTreePage}/>
                <Route path="/tree/people" component={ViewTreePeoplePage}/>
                <Route path="/profile" component={PersonProfilePage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<PeopleRouter/>, document.getElementById("main"));