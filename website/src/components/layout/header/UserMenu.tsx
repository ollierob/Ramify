import * as  React from "react";
import {Button} from "antd";

export default class UserMenu extends React.PureComponent {

    render() {

        return <div className="user">
            <Button>Register or sign in</Button>
        </div>;

    }

}