import { NextRequest, NextResponse } from "next/server"

export async function POST(request: NextRequest) {
    const refresh = request.cookies.get("refreshToken")?.value;

    if(refresh) {
        await fetch(`${process.env.BASE_API}/api/v1/auth/logout`, {
            method: "POST",
        }).catch(() => {});
    }

    const res = new NextResponse(null, { status: 204 });
    res.cookies.set("accessToken", "", {
        path: "/",
        httpOnly: true,
        secure: true,
        sameSite: "strict",
        maxAge: 0,
    });
    res.cookies.set("refreshToken", "", {
        path: "/api/auth",
        httpOnly: true,
        secure: true,
        sameSite: "strict",
        maxAge: 0,
    });
    
    return res;
}