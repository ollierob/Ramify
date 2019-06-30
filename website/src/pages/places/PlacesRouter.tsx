import * as React from "react";
import * as ReactDOM from "react-dom";
import {HashRouter, Route, Switch} from "react-router-dom";

const App = () =>
    <HashRouter>
        <Switch>
            <Route exact path="" component={null}/>
        </Switch>
    </HashRouter>;

ReactDOM.render(<App/>, document.getElementById("main"));