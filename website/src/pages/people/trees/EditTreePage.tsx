import * as React from "react";
import {ManageTreePage} from "./ManageTreePage";
import {Card} from "antd";

type State = {};

export class EditTreePage extends ManageTreePage<State> {

    main() {

        return <Card
            className="main large"
            title="Edit Family Tree">

        </Card>;

    }

}