import * as React from "react";
import {FamilyTree} from "../../protobuf/generated/family_pb";
import {AsyncData} from "../fetch/AsyncData";
import Draggable from "./Draggable";
import "./FamilyTreeViewer.css"

type Props = {
    content?: boolean;
    tree: AsyncData<FamilyTree.AsObject>
}

export class FamilyTreeViewer extends React.PureComponent<Props> {

    render() {

        return <div className={"treeViewer" + (this.props.content ? " content" : "")}>

            <Draggable>

                <div style={{border: "1px solid black", display: "inline-block"}}>Hello</div>

            </Draggable>

        </div>;

    }

}