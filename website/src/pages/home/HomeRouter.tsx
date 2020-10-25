import * as React from "react";
import {HashRouter, Route} from "react-router-dom";
import {Switch} from "antd";
import * as ReactDOM from "react-dom";
import HomePage from "./HomePage";
import LoginPage from "./LoginPage";

class HomeRouter extends React.PureComponent {

    render() {
        return <HashRouter>
            <Switch>
                <Route path="/login" component={LoginPage}/>
                <Route exact path="/" component={HomePage}/>
            </Switch>
        </HashRouter>;
    }

}

ReactDOM.render(<HomeRouter/>, document.getElementById("main"));