import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, Switch} from "react-router-dom";
import RecordSearchPage from "./search/RecordSearchPage";
import RecordSetPage from "./set/RecordSetPage";
import "./Records.css";

class RecordsRootPage extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={RecordSearchPage}/>
                <Route path="/search" component={RecordSearchPage}/>
                <Route path="/set" component={RecordSetPage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<RecordsRootPage/>, document.getElementById("main"));