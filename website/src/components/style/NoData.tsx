import * as React from "react";
import {Empty} from "antd";

export const NoData = (props: {text?: string, children?: React.ReactNode}) => <Empty description={props.text} className="noData">{props.children}</Empty>;