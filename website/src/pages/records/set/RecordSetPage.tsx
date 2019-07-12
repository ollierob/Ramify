import * as React from "react";
import {AsyncData} from "../../../components/fetch/AsyncData";
import {RecordSet} from "../../../protobuf/generated/record_pb";

type Props = {}

type State = {
    recordSet: AsyncData<RecordSet.AsObject>
}

export default class RecordSetPage extends React.PureComponent<Props, State> {

    constructor(props) {
        super(props);
        this.state = {
            recordSet: {}
        }
    }

    render() {

        return <>

            <h1>Record Set</h1>

        </>;

    }

}