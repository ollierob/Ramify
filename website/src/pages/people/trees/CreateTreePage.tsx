import * as React from "react";
import {ManageTreePage} from "./ManageTreePage";
import {Button, Card, Form, Icon, Input, Tabs} from "antd";

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
    name?: string;
};

class ManuallyCreateTree extends React.PureComponent<{}, ManualState> {

    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {

        return <Form>

            <Form.Item>

                Tree name:
                <br/>

                <Input
                    size="large"
                    style={{width: 300}}
                    prefix={<Icon type="edit" className="gray"/>}
                    value={this.state.name}
                    onChange={e => this.setState({name: e.target.value})}/>

            </Form.Item>

            <Form.Item>

                Initial person:
                <br/>

            </Form.Item>

            <Form.Item>

                <Button
                    size="large"
                    type="primary"
                    icon="plus-circle"
                    disabled={!this.state.name}>
                    Create
                </Button>

            </Form.Item>

        </Form>;

    }

}