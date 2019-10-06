import * as React from "react";
import {Person} from "../../../protobuf/generated/person_pb";
import {EventList} from "../../../components/event/EventList";
import {HasClass} from "../../../components/style/HasClass";
import {Card} from "antd";

type Props = {
    person: Person.AsObject
    loading: boolean
}

export class PersonProfile extends React.PureComponent<Props> {

    render() {

        return <div className="profile content">

            {this.props.person && <>

                <Gallery
                    className="large"/>

                <EventList
                    className="large"
                    events={this.props.person.eventsList}/>

            </>}

        </div>;

    }

}

const Gallery = (props: HasClass) => {
    return <Card
        title="Gallery"
        className={"gallery " + (props.className || "")}
        style={props.style}>

    </Card>;
};