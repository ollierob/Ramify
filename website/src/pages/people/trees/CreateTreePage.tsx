import * as React from "react";
import {ManageTreePage} from "./ManageTreePage";
import {Button, Card, Form, Icon, Input, Tabs} from "antd";
import {CSSProperties} from "react";
import {EditIcon, PeopleIcon} from "../../../components/images/Icons";

export class CreateTreePage extends ManageTreePage {

    main() {

        return <Card
            className="main large"
            title="Create Family Tree">

            <Tabs size="large" defaultActiveKey="manual">

                <Tabs.TabPane key="manual" tab="Create manually">
                    <ManuallyCreateTree/>
                </Tabs.TabPane>

                <Tabs.TabPane key="gedcom" tab="Upload GEDCOM" disabled/>

            </Tabs>

        </Card>;

    }

}

type ManualState = {
    treeName?: string;
    firstName?: string;
    lastName?: string;
};

class ManuallyCreateTree extends React.PureComponent<{}, ManualState> {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {

        return <div className="manual">

            <Card title="Tree name" size="small" bordered={false}>

                <Input
                    size="large"
                    prefix={<EditIcon className="gray"/>}
                    placeholder="Name"
                    value={this.state.treeName}
                    onChange={e => this.setState({treeName: e.target.value})}/>

            </Card>

            <Card title="Initial person" size="small" bordered={false}>

                <Input
                    size="large"
                    prefix={<PeopleIcon className="gray"/>}
                    placeholder="First names"
                    value={this.state.firstName}
                    onChange={e => this.setState({firstName: e.target.value})}/>

                <br/>

                <Input
                    size="large"
                    style={{marginTop: 4}}
                    prefix={<PeopleIcon className="gray"/>}
                    placeholder="Last name"
                    value={this.state.lastName}
                    onChange={e => this.setState({lastName: e.target.value})}/>

            </Card>

            <div style={{clear: "both"}}>
                <Button
                    size="large"
                    type="primary"
                    disabled={!this.canSubmit()}>
                    Create tree
                </Button>
            </div>

        </div>;

    }

    private canSubmit(): boolean {
        return !!this.state.treeName
            && !!this.state.firstName
            && !!this.state.lastName;
    }

}