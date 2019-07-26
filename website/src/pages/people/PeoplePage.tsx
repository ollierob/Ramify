import * as ReactDOM from "react-dom";
import * as React from "react";
import {Route, Switch} from "react-router";
import {HashRouter} from "react-router-dom";
import PeopleSearchPage from "./search/PeopleSearchPage";

class PeoplePage extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={PeopleSearchPage}/>
                <Route path="/search" component={PeopleSearchPage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<PeoplePage/>, document.getElementById("main"));