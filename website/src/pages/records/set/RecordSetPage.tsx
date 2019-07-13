import * as React from "react";
import {AsyncData, asyncLoadData} from "../../../components/fetch/AsyncData";
import {RecordSet} from "../../../protobuf/generated/record_pb";
import {RouteComponentProps} from "react-router";
import {DEFAULT_RECORD_LOADER} from "../../../components/records/RecordLoader";
import {Loading} from "../../../components/Loading";
import {Card} from "antd";

type Props = RouteComponentProps<any>

type State = {
    recordSetId?: string;
    recordSet: AsyncData<RecordSet.AsObject>
}

export default class RecordSetPage extends React.PureComponent<Props, State> {

    private readonly recordLoader = DEFAULT_RECORD_LOADER;

    constructor(props) {
        super(props);
        this.state = {
            recordSet: {}
        }
    }

    render() {

        if (this.state.recordSet.loading) return <Loading/>;

        const data = this.state.recordSet.data;
        if (!data) return null;

        return <Card
            className="recordSet"
            title={<b>{data.title}</b>}>

            <div className="description">
                {data.description}
            </div>

        </Card>;

    }

    componentDidMount() {
        this.readLocation();
    }

    componentDidUpdate(prevProps: Readonly<Props>, prevState: Readonly<State>) {
        if (this.props.location != prevProps.location)
            this.readLocation();
        if (this.state.recordSetId != prevState.recordSetId)
            this.loadRecordSet(this.state.recordSetId);
    }

    private readLocation() {
        const location = this.props.location;
        if (!location) return;
        const search = new URLSearchParams(location.search);
        this.setState({recordSetId: search.get("id")})
    }

    private loadRecordSet(id: string) {
        if (!id) return;
        asyncLoadData(id, this.recordLoader.loadRecordSet, recordSet => this.setState({recordSet}))
    }

}