import * as  React from "react";
import {ProfileEvent} from "../../../components/event/ProfileEvent";

type Props = {
    events: ReadonlyArray<ProfileEvent>,
    selectedEvent: ProfileEvent
}

type State = {}

export default class PersonProfileSources extends React.PureComponent<Props, State> {

    render() {

        return <div className="sources">


        </div>;

    }

}