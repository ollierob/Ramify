import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, Switch} from "react-router-dom";
import RecordsHomePage from "./home/RecordsHomePage";
import RecordSetPage from "./set/RecordSetPage";
import "./Records.css";

class RecordsPage extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route exact path="/" component={RecordsHomePage}/>
                <Route path="/search" component={RecordsHomePage}/>
                <Route path="/set" component={RecordSetPage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<RecordsPage/>, document.getElementById("main"));