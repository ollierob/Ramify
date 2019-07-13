import * as React from "react";
import {HasClass} from "../style/HasClass";
import "./Search.css";
import {Button, Form, Input} from "antd";
import {ChangeEvent} from "react";
import {LoadingIcon, SearchIcon} from "../images/Icons";

type Props = HasClass & {
    searching: boolean;
    disabled?: boolean;
    doSearch: (firstName: string, lastName: string) => void;
}

type State = {
    firstName?: string
    lastName?: string
}

export class NameSearch extends React.PureComponent<Props, State> {

    private setFirstName = (e: ChangeEvent<HTMLInputElement>) => this.setState({firstName: e.target.value});
    private setLastName = (e: ChangeEvent<HTMLInputElement>) => this.setState({lastName: e.target.value});

    constructor(props) {
        super(props);
        this.state = {};
        this.doSubmit = this.doSubmit.bind(this);
    }


    render() {

        return <div className={"search " + (this.props.className || "")} style={this.props.style}>

            Search for a person:

            <Form layout="inline" onSubmit={this.doSubmit}>

                <Form.Item>
                    <Input
                        className="firstName"
                        size="large"
                        placeholder="First name(s) or initials"
                        value={this.state.firstName}
                        onChange={this.setFirstName}/>
                </Form.Item>

                <Form.Item>
                    <Input
                        className="lastName"
                        size="large"
                        placeholder="Family name"
                        value={this.state.lastName}
                        onChange={this.setLastName}/>
                </Form.Item>

                <Form.Item>
                    <Button
                        type="primary"
                        htmlType="submit"
                        size="large"
                        disabled={this.props.disabled || this.props.searching}>
                        {this.props.searching
                            ? <><LoadingIcon/> Searching</>
                            : <><SearchIcon/> Search</>}
                    </Button>
                </Form.Item>

            </Form>

        </div>;

    }

    private doSubmit() {
        this.props.doSearch(this.state.firstName, this.state.lastName);
    }

}