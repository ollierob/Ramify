import * as React from "react";
import {Link as LinkProto} from "../../protobuf/generated/link_pb";
import {Icon} from "antd";
import TypeMapValues = LinkProto.Type;

export const Links = (props: {links: ReadonlyArray<LinkProto.AsObject>}) => {
    const links = props.links;
    if (!links || !links.length) return null;
    return <div className="links">
        {links.map(link => <Link link={link}/>)}
    </div>;
};

export const Link = (props: {link: LinkProto.AsObject, children?: React.ReactNode, newWindow?: boolean}) => {
    const link = props.link;
    if (!link) return null;
    const icon = linkIcon(link);
    return <a href={link.href} target={props.newWindow ? "_blank" : "_self"}>
        {icon || <Icon type="link"/>}
        {props.children && <> {props.children}</>}
    </a>;
};

function linkIcon(link: LinkProto.AsObject): React.ReactNode {
    switch (link.type) {
        case TypeMapValues.WIKIPEDIA:
            return <img src="/images/links/wikipedia.png" className="image" alt="Wikipedia"/>;
        case TypeMapValues.WYAS:
            return <img src="/images/links/wyas.png" className="image" alt="WYAS"/>;
        default:
            return null;
    }
}