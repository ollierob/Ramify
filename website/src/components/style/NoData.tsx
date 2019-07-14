import * as React from "react";
import {Empty} from "antd";

export const NoData = (props: {children?: React.ReactNode}) => <Empty className="noData">{props.children}</Empty>;