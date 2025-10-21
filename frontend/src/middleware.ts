import { NextRequest, NextResponse } from "next/server";

const DASHBOARD = ["/bookings", "/rooms", "/admin"];

export function middleware(req: NextRequest) {
    if(DASHBOARD.some(p => req.nextUrl.pathname.startsWith(p))) {
        const access = req.cookies.get(process.env.ACCESS_COOKIE!);

        if(!access) {
            return NextResponse.redirect(new URL("/login", req.url));
        }
    }

    return NextResponse.next();
}

export const config = { matcher: ["/bookings/:path*", "/rooms/:path*", "/admin/:path*"] };