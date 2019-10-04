import * as React from "react";
import {CSSProperties, MouseEvent, RefObject} from "react";

type Props = {
    children: React.ReactNode
}

type State = {
    mouseDown?: boolean;
    dragging?: boolean;
}

export default class Draggable extends React.PureComponent<Props, State> {

    private readonly div: RefObject<HTMLDivElement>;

    constructor(props) {
        super(props);
        this.state = {};
        this.div = React.createRef();
        this.onMouseUp = this.onMouseUp.bind(this);
        this.onMouseMove = this.onMouseMove.bind(this);
        this.onMouseDown = this.onMouseDown.bind(this);
        this.preventDefault = this.preventDefault.bind(this);
    }

    render() {
        return <div
            ref={this.div}
            onMouseUp={this.onMouseUp}
            onMouseMove={this.onMouseMove}
            onMouseDown={this.onMouseDown}
            onDragStart={this.preventDefault}
            style={this.state.mouseDown ? DraggingStyle : DraggableStyle}>

            {this.props.children}

        </div>;
    }

    componentDidMount() {
        this.div.current.addEventListener("selectstart", this.preventDefault);
    }

    private preventDefault(e: Event | MouseEvent) {
        e.preventDefault();
    }

    private onMouseDown() {
        this.setState({mouseDown: true, dragging: false});
    }

    private onMouseMove(e: MouseEvent) {
        if (!this.state.mouseDown) return;
    }

    private onMouseUp() {
        this.setState({mouseDown: false, dragging: false});
    }

    componentWillUnmount() {
        this.div.current.removeEventListener("selectstart", this.preventDefault);
    }

}

const DraggableStyle: CSSProperties = {cursor: "grab"};
const DraggingStyle: CSSProperties = {cursor: "grabbing"};