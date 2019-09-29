import * as React from "react";
import {PeopleBasePage} from "../PeopleBasePage";
import "./ManageTree.css";
import {TreeNavigationMenu} from "./TreeNavigationMenu";

export abstract class ManageTreePage<S> extends PeopleBasePage<S> {

    body() {

        return <div className="manageTree content">

            <div className="left">
                {this.navigation()}
            </div>

            <div className="right">
                {this.main()}
            </div>

        </div>;

    }

    navigation() {
        return <TreeNavigationMenu/>;
    }

    abstract main(): React.ReactNode

}