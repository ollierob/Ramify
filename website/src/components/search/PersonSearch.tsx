import * as React from "react";
import {HasClass} from "../style/HasClass";
import "./Search.css";
import {Button, Form, Input, InputNumber} from "antd";
import {ChangeEvent, FormEvent} from "react";
import {CancelIcon, LoadingIcon, SearchIcon} from "../images/Icons";
import {RecordSearch} from "../../protobuf/generated/record_pb";
import {RecordSearchHandler} from "./RecordSearchHandler";

type Props = HasClass & RecordSearchHandler & {
    disabled?: boolean;
}

type State = {
    firstName?: string
    lastName?: string
    age?: number;
}

export class PersonSearch extends React.PureComponent<Props, State> {

    private setFirstName = (e: ChangeEvent<HTMLInputElement>) => this.setState({firstName: e.target.value});
    private setLastName = (e: ChangeEvent<HTMLInputElement>) => this.setState({lastName: e.target.value});
    private setAge = (age: number) => this.setState({age});

    constructor(props) {
        super(props);
        this.state = {};
        this.doSubmit = this.doSubmit.bind(this);
        this.clearSearch = this.clearSearch.bind(this);
    }

    render() {

        return <div className={"search " + (this.props.className || "")} style={this.props.style}>

            Search for a person:

            <Form layout="inline" onSubmit={this.doSubmit}>

                <Form.Item>
                    <Input
                        className="firstName"
                        size="large"
                        placeholder="Forename(s) or initials"
                        value={this.state.firstName}
                        onChange={this.setFirstName}/>
                </Form.Item>

                <Form.Item>
                    <Input
                        className="lastName"
                        size="large"
                        placeholder="Surname"
                        value={this.state.lastName}
                        onChange={this.setLastName}/>
                </Form.Item>

                <Form.Item>
                    <InputNumber
                        min={0}
                        max={125}
                        maxLength={3}
                        width={120}
                        size="large"
                        placeholder="Age or birth year"
                        value={this.state.age}
                        onChange={this.setAge}/>
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

                <Form.Item>
                    <Button
                        size="large"
                        onClick={this.clearSearch}
                        disabled={this.props.disabled}>
                        <CancelIcon/> Clear
                    </Button>
                </Form.Item>

            </Form>

        </div>;

    }

    private doSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const search = new RecordSearch();
        if (this.state.firstName) search.setFirstname(this.state.firstName);
        if (this.state.lastName) search.setLastname(this.state.lastName);
        if (this.state.age != null) search.setAge(this.state.age);
        this.props.doSearch(search);
    }

    private clearSearch() {
        this.setState({firstName: null, lastName: null, age: null});
    }

}