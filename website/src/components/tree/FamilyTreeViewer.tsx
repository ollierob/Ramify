import * as React from "react";
import {FamilyTree} from "../../protobuf/generated/family_pb";
import {AsyncData} from "../fetch/AsyncData";

type Props = {
    content?: boolean;
    tree: AsyncData<FamilyTree.AsObject>
}

export class FamilyTreeViewer extends React.PureComponent<Props> {

    render() {

        return <div className={"treeViewer" + (this.props.content ? " content" : "")}>

        </div>;

    }

}