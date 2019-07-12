import * as React from "react";
import {Link as LinkProto} from "../../protobuf/generated/link_pb";
import TypeMap = LinkProto.Type;
import {Icon} from "antd";

export const Links = (props: {links: ReadonlyArray<LinkProto.AsObject>}) => {
    const links = props.links;
    if (!links || !links.length) return null;
    return <div className="links">
        {links.map(link => <Link link={link}/>)}
    </div>
};

export const Link = (props: {link: LinkProto.AsObject}) => {
    const link = props.link;
    if (!link) return null;
    if (link.type == TypeMap.WIKIPEDIA) return <a href={"https://" + link.href} target="_blank"><img src="/images/wikipedia.png"/></a>;
    return <a href={"http://" + link.href}><Icon type="link"/></a>;
};